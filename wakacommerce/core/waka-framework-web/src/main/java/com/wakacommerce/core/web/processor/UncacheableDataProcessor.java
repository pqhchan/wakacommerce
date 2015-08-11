package com.wakacommerce.core.web.processor;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Macro;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import com.wakacommerce.cms.web.PageHandlerMapping;
import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.common.page.dto.PageDTO;
import com.wakacommerce.common.security.service.ExploitProtectionService;
import com.wakacommerce.common.util.StringUtil;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductOptionXref;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.inventory.service.InventoryService;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.SkuAccessor;
import com.wakacommerce.core.web.order.CartState;
import com.wakacommerce.core.web.processor.extension.UncacheableDataProcessorExtensionManager;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.web.core.CustomerState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class UncacheableDataProcessor extends AbstractElementProcessor {
    
    @Value("${solr.index.use.sku}")
    protected boolean useSku;

    @Resource(name = "blInventoryService")
    protected InventoryService inventoryService;

    @Resource(name = "blExploitProtectionService")
    protected ExploitProtectionService eps;

    @Resource(name = "blUncacheableDataProcessorExtensionManager")
    protected UncacheableDataProcessorExtensionManager extensionManager;

    private String defaultCallbackFunction = "updateUncacheableData(params)";

    public UncacheableDataProcessor() {
        super("uncacheabledata");
    }

    @Override
    public int getPrecedence() {
        return 100;
    }


    @Override
    protected ProcessorResult processElement(Arguments arguments, Element element) {
        StringBuffer sb = new StringBuffer();
        sb.append("<SCRIPT>\n");
        sb.append("  var params = \n  ");
        sb.append(buildContentMap(arguments)).append(";\n  ");
        sb.append(getUncacheableDataFunction(arguments, element)).append(";\n");
        sb.append("</SCRIPT>");
                
        // Add contentNode to the document
        Node contentNode = new Macro(sb.toString());
        element.clearChildren();
        element.getParent().insertAfter(element, contentNode);
        element.getParent().removeChild(element);

        // Return OK
        return ProcessorResult.OK;

    }

    protected String buildContentMap(Arguments arguments) {
        Map<String, Object> attrMap = new HashMap<String, Object>();
        addCartData(attrMap);
        addCustomerData(attrMap);
        addProductInventoryData(attrMap, arguments);

        try {
            attrMap.put("csrfToken", eps.getCSRFToken());
            attrMap.put("csrfTokenParameter", eps.getCsrfTokenParameter());
        } catch (ServiceException e) {
            throw new RuntimeException("Could not get a CSRF token for this session", e);
        }
        return StringUtil.getMapAsJson(attrMap);
    }

    protected void addProductInventoryData(Map<String, Object> attrMap, Arguments arguments) {
        List<Long> outOfStockProducts = new ArrayList<Long>();
        List<Long> outOfStockSkus = new ArrayList<Long>();

        Set<Product> allProducts = new HashSet<Product>();
        Set<Sku> allSkus = new HashSet<Sku>();
        Set<Product> products = (Set<Product>) ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).get("blcAllDisplayedProducts");
        Set<Sku> skus = (Set<Sku>) ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).get("blcAllDisplayedSkus");
        if (!CollectionUtils.isEmpty(products)) {
            allProducts.addAll(products);
        }
        if (!CollectionUtils.isEmpty(skus)) {
            allSkus.addAll(skus);
        }

        extensionManager.getProxy().modifyProductListForInventoryCheck(arguments, allProducts, allSkus);

        if (!allProducts.isEmpty()) {
            for (Product product : allProducts) {
                if (product.getDefaultSku() != null) {

                    Boolean qtyAvailable = inventoryService.isAvailable(product.getDefaultSku(), 1);
                    if (qtyAvailable != null && !qtyAvailable) {
                        outOfStockProducts.add(product.getId());
                    }
                }
            }
        } else {
            if (!allSkus.isEmpty()) {
                Map<Sku, Integer> inventoryAvailable = inventoryService.retrieveQuantitiesAvailable(allSkus);
                for (Map.Entry<Sku, Integer> entry : inventoryAvailable.entrySet()) {
                    if (entry.getValue() == null || entry.getValue() < 1) {
                        outOfStockSkus.add(entry.getKey().getId());
                    }
                }
            }
        }
        attrMap.put("outOfStockProducts", outOfStockProducts);
        attrMap.put("outOfStockSkus", outOfStockSkus);
    }

    protected void addCartData(Map<String, Object> attrMap) {
        Order cart = CartState.getCart();
        int cartQty = 0;
        List<Long> cartItemIdsWithOptions = new ArrayList<Long>();
        List<Long> cartItemIdsWithoutOptions = new ArrayList<Long>();

        if (cart != null && cart.getOrderItems() != null) {
            cartQty = cart.getItemCount();

            for (OrderItem item : cart.getOrderItems()) {
                if (item instanceof SkuAccessor) {
                    Sku sku = ((SkuAccessor) item).getSku();
                    if (sku != null && sku.getProduct() != null) {
                        if (useSku) {
                            cartItemIdsWithoutOptions.add(sku.getId());
                        } else {
                            Product product = sku.getProduct();
                            List<ProductOptionXref> optionXrefs = product.getProductOptionXrefs();
                            if (optionXrefs == null || optionXrefs.isEmpty()) {
                                cartItemIdsWithoutOptions.add(product.getId());
                            } else {
                                cartItemIdsWithOptions.add(product.getId());
                            } 
                        }
                        
                    }
                }
            }
        }

        attrMap.put("cartItemCount", cartQty);
        attrMap.put("cartItemIdsWithOptions", cartItemIdsWithOptions);
        attrMap.put("cartItemIdsWithoutOptions", cartItemIdsWithoutOptions);
    }
    
    protected void addCustomerData(Map<String, Object> attrMap) {
        Customer customer = CustomerState.getCustomer();
        String realName = "";
        boolean anonymous = false;

        if (customer != null) {
            if (!StringUtils.isEmpty(customer.getRealName())) {
            	realName = customer.getRealName();
            }

            if (customer.isAnonymous()) {
                anonymous = true;
            }
        }
        
        attrMap.put("realName", realName);
        attrMap.put("anonymous", anonymous);
    }
    
    public String getUncacheableDataFunction(Arguments arguments, Element element) {
        if (element.hasAttribute("callback")) {
            return element.getAttributeValue("callback");
        } else {
            return getDefaultCallbackFunction();
        }
    }

    public String getDefaultCallbackFunction() {
        return defaultCallbackFunction;
    }
    
    public void setDefaultCallbackFunction(String defaultCallbackFunction) {
        this.defaultCallbackFunction = defaultCallbackFunction;
    }
}

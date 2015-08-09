
package com.wakacommerce.core.web.processor;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.wakacommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.PromotableProduct;
import com.wakacommerce.core.catalog.domain.RelatedProductDTO;
import com.wakacommerce.core.catalog.domain.RelatedProductTypeEnum;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.catalog.service.RelatedProductsService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;


/**
 *
 * @ hui
 */
public class RelatedProductProcessor extends AbstractModelVariableModifierProcessor {
    
    @Value("${solr.index.use.sku}")
    protected boolean useSku;
    
    @Resource(name = "blRelatedProductsService")
    protected RelatedProductsService relatedProductsService;

    public RelatedProductProcessor() {
        super("related_products");
    }
    
    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override

    protected void modifyModelAttributes(Arguments arguments, Element element) {
        RelatedProductDTO relatedProductDTO = buildDTO(arguments, element);
        List<? extends PromotableProduct> relatedProducts = relatedProductsService.findRelatedProducts(relatedProductDTO);
        if (useSku) {
            addToModel(arguments, getRelatedSkusResultVar(element), getRelatedSkus(relatedProducts, relatedProductDTO.getQuantity()));
        } else {
            addToModel(arguments, getRelatedProductsResultVar(element), relatedProducts);
            addToModel(arguments, getProductsResultVar(element), convertRelatedProductsToProducts(relatedProducts));
            addCollectionToExistingSet(arguments, "blcAllProducts", buildProductList(relatedProducts));
        }
    }

    protected List<Product> buildProductList(List<? extends PromotableProduct> relatedProducts) {
        List<Product> productList = new ArrayList<Product>();
        if (relatedProducts != null) {
            for (PromotableProduct promProduct : relatedProducts) {
                productList.add(promProduct.getRelatedProduct());
            }
        }
        return productList;
    }
    
    protected List<Sku> getRelatedSkus(List<? extends PromotableProduct> relatedProducts, Integer maxQuantity) {
        List<Sku> relatedSkus = new ArrayList<Sku>();
        if (relatedProducts != null) {
            Integer numSkus = 0;
            for (PromotableProduct promProduct : relatedProducts) {
                Product relatedProduct = promProduct.getRelatedProduct();
                List<Sku> additionalSkus = relatedProduct.getAdditionalSkus();
                if(CollectionUtils.isNotEmpty(additionalSkus)) {
                    for(Sku additionalSku : additionalSkus) {
                        if(numSkus == maxQuantity) {
                            break;
                        }
                        relatedSkus.add(additionalSku);
                        numSkus++;
                        
                    }
                } else {
                    if(numSkus.equals(maxQuantity)) {
                        break;
                    }
                    relatedSkus.add(relatedProduct.getDefaultSku());
                    numSkus++;
                }
            }
        }
        return relatedSkus;
    }
    
    protected List<Product> convertRelatedProductsToProducts(List<? extends PromotableProduct> relatedProducts) {
        List<Product> products = new ArrayList<Product>();
        if (relatedProducts != null) {
            for (PromotableProduct product : relatedProducts) {
                products.add(product.getRelatedProduct());
            }
        }
        return products;        
    }
    
    private String getRelatedProductsResultVar(Element element) {
        String resultVar = element.getAttributeValue("relatedProductsResultVar");       
        if (resultVar == null) {
            resultVar = "relatedProducts";
        }
        return resultVar;
    }
    
    private String getRelatedSkusResultVar(Element element) {
        String resultVar = element.getAttributeValue("relatedSkusResultVar");       
        if (resultVar == null) {
            resultVar = "relatedSkus";
        }
        return resultVar;
    }
    
    private String getProductsResultVar(Element element) {
        String resultVar = element.getAttributeValue("productsResultVar");      
        if (resultVar == null) {
            resultVar = "products";
        }
        return resultVar;
    }

    private RelatedProductDTO buildDTO(Arguments args, Element element) {
        RelatedProductDTO relatedProductDTO = new RelatedProductDTO();
        String productIdStr = element.getAttributeValue("productId"); 
        String categoryIdStr = element.getAttributeValue("categoryId"); 
        String quantityStr = element.getAttributeValue("quantity"); 
        String typeStr = element.getAttributeValue("type"); 
        
        if (productIdStr != null) {
            Expression expression = (Expression) StandardExpressions.getExpressionParser(args.getConfiguration())
                    .parseExpression(args.getConfiguration(), args, productIdStr);
            Object productId = expression.execute(args.getConfiguration(), args);
            if (productId instanceof BigDecimal) {
                productId = new Long(((BigDecimal) productId).toPlainString());
            }
            relatedProductDTO.setProductId((Long) productId);
        }
        
        if (categoryIdStr != null) {
            Expression expression = (Expression) StandardExpressions.getExpressionParser(args.getConfiguration())
                    .parseExpression(args.getConfiguration(), args, categoryIdStr);
            Object categoryId = expression.execute(args.getConfiguration(), args);
            if (categoryId instanceof BigDecimal) {
                categoryId = new Long(((BigDecimal) categoryId).toPlainString());
            }
            relatedProductDTO.setCategoryId((Long) categoryId);         
        }
        
        if (quantityStr != null) {
            Expression expression = (Expression) StandardExpressions.getExpressionParser(args.getConfiguration())
                    .parseExpression(args.getConfiguration(), args, quantityStr);
            Object quantityExp = expression.execute(args.getConfiguration(), args);
            int quantity = 0;
            if (quantityExp instanceof String) {
                quantity = Integer.parseInt((String)quantityExp);
            } else {
                quantity = ((BigDecimal)expression.execute(args.getConfiguration(), args)).intValue();
            }
            relatedProductDTO.setQuantity(quantity);          
        }       
                
        if (typeStr != null ) {
            Expression expression = (Expression) StandardExpressions.getExpressionParser(args.getConfiguration())
                    .parseExpression(args.getConfiguration(), args, typeStr);
            Object typeExp = expression.execute(args.getConfiguration(), args);
            if (typeExp instanceof String && RelatedProductTypeEnum.getInstance((String)typeExp) != null) {
                relatedProductDTO.setType(RelatedProductTypeEnum.getInstance((String)typeExp));
            }

        }
        
        if ("false".equalsIgnoreCase(element.getAttributeValue("cumulativeResults"))) {
            relatedProductDTO.setCumulativeResults(false);          
        }
                    
        return relatedProductDTO;
    }
}

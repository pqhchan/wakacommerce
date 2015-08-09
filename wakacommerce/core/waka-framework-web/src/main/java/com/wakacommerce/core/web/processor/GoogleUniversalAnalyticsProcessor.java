
package com.wakacommerce.core.web.processor;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Macro;
import org.thymeleaf.dom.Node;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.core.catalog.domain.Sku;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.OrderItemAttribute;
import com.wakacommerce.core.order.domain.SkuAccessor;
import com.wakacommerce.core.order.service.OrderService;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public class GoogleUniversalAnalyticsProcessor extends AbstractElementProcessor {

    private static final Log LOG = LogFactory.getLog(GoogleUniversalAnalyticsProcessor.class);

    @Value("${googleAnalytics.masterWebPropertyId}")
    protected String masterWebPropertyId;
    
    @Resource(name = "blOrderService")
    protected OrderService orderService;

    @Value("${googleAnalytics.testLocal}")
    protected boolean testLocal = false;
    
    public GoogleUniversalAnalyticsProcessor() {
        super("google_universal_analytics");
    }
    
    public GoogleUniversalAnalyticsProcessor(String elementName) {
        super(elementName);
    }
    
    @Override
    public int getPrecedence() {
        return 0;
    }

    @Override
    protected ProcessorResult processElement(Arguments arguments, Element element) {
        StringBuffer sb = new StringBuffer();
        Map<String, String> trackers = getTrackers();
        if (MapUtils.isNotEmpty(trackers)) {
            sb.append("<script>\n");
            sb.append("(function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){");
            sb.append("(i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),");
            sb.append("m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)");
            sb.append("})(window,document,'script','//www.google-analytics.com/analytics.js','ga');");
            
            String orderNumberExpression = element.getAttributeValue("ordernumber");
            String orderNumber = null;
            if (orderNumberExpression != null) {
                final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(arguments.getConfiguration());
                Expression expression = (Expression) expressionParser.parseExpression(arguments.getConfiguration(), arguments, orderNumberExpression);
                orderNumber = (String) expression.execute(arguments.getConfiguration(), arguments);
            }
            
            Order order = null;
            if (orderNumber != null) {
                order = orderService.findOrderByOrderNumber(orderNumber);
            }
            
            for (Entry<String, String> tracker : trackers.entrySet()) {
                String trackerName = tracker.getKey();
                String trackerPrefix = "";
                String id = tracker.getValue();
                sb.append("ga('create', '" + id + "', 'auto', {");

                if (!"webProperty".equals(trackerName)) {
                    trackerPrefix = trackerName + ".";
                    sb.append("'name': '" + trackerName + "'");
                    if (testLocal) {
                        sb.append(",");
                    }
                }
                if (testLocal) {
                    sb.append("'cookieDomain': 'none'");
                }
                sb.append("});");

                if ("webProperty".equals(trackerName)) {
                    HttpServletRequest request = WakaRequestContext.getWakaRequestContext().getRequest();
                    if (request != null) {
                        Map<String, String> setValuesMap = (Map<String, String>) request.getAttribute("blGAValuesMap");
                        if (setValuesMap != null) {
                            for (Map.Entry<String, String> entry : setValuesMap.entrySet()) {
                                sb.append("ga('" + trackerPrefix + "set',").append(entry.getKey()).append(",")
                                        .append(entry.getValue()).append(");");
                            }
                        }
                    }
                }

                sb.append("ga('" + trackerPrefix + "send', 'pageview');");
                
                if (isIncludeLinkAttribution()) {
                    sb.append(getLinkAttributionJs(trackerPrefix));
                }
                if (isIncludeDisplayAdvertising()) {
                    sb.append(getDisplayAdvertisingJs(trackerPrefix));
                }
                
                if (order != null) {
                    sb.append(getTransactionJs(order, trackerPrefix));
                }
            }
            
            sb.append("</script>");
            
            // Add contentNode to the document
            Node contentNode = new Macro(sb.toString());
            element.clearChildren();
            element.getParent().insertAfter(element, contentNode);
            element.getParent().removeChild(element);
        } else {
            LOG.warn("No trackers were found, not outputting Google Analytics script. Set the googleAnalytics.webPropertyId"
                    + " and/or the googleAnalytics.masterWebPropertyId system properties to output Google Analytics");
        }

        // Return OK
        return ProcessorResult.OK;
    }

    protected Map<String, String> getTrackers() {
        Map<String, String> trackers = new HashMap<String, String>();
        if (shouldShowMasterTracker()) {
            trackers.put("master", getMasterWebPropertyId());
        }
        if (StringUtils.isNotBlank(getWebPropertyId())) {
            trackers.put("webProperty", getWebPropertyId());
        }
        
        return trackers;
    }
    
    protected boolean shouldShowMasterTracker() {
        String masterWebPropertyId = getMasterWebPropertyId();
        return (StringUtils.isNotBlank(masterWebPropertyId) && (!"UA-XXXXXXX-X".equals(masterWebPropertyId)));
    }

    protected String getLinkAttributionJs(String trackerPrefix) {
        return "ga('" + trackerPrefix + "require', 'linkid', 'linkid.js');";
    }

    protected String getDisplayAdvertisingJs(String trackerPrefix) {
        return "ga('" + trackerPrefix + "require', 'displayfeatures');";
    }

    protected String getTransactionJs(Order order, String trackerPrefix) {
        StringBuffer sb = new StringBuffer();
        sb.append("ga('" + trackerPrefix + "require', 'ecommerce', 'ecommerce.js');");
        
        sb.append("ga('" + trackerPrefix + "ecommerce:addTransaction', {");
        sb.append("'id': '" + order.getOrderNumber() + "'");
        if (StringUtils.isNotBlank(getAffiliation())) {
            sb.append(",'affiliation': '" + getAffiliation() + "'");
        }
        sb.append(",'revenue': '" + order.getTotal() + "'");
        sb.append(",'shipping':'" + order.getTotalShipping() + "'");
        sb.append(",'tax': '" + order.getTotalTax() + "'");

        if (order.getCurrency() != null) {
            sb.append(",'currency': '" + order.getCurrency().getCurrencyCode() + "'");
        }
        sb.append("});");

        sb.append(getItemJs(order, trackerPrefix));
        
        sb.append("ga('" + trackerPrefix + "ecommerce:send');");
        return sb.toString();
    }
    
    protected String getItemJs(Order order, String trackerPrefix) {
        StringBuffer sb = new StringBuffer();
        for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
            for (FulfillmentGroupItem fulfillmentGroupItem : fulfillmentGroup.getFulfillmentGroupItems()) {
                OrderItem orderItem = fulfillmentGroupItem.getOrderItem();
    
                Sku sku = ((SkuAccessor) orderItem).getSku();
                
                sb.append("ga('" + trackerPrefix + "ecommerce:addItem', {");
                sb.append("'id': '" + order.getOrderNumber() + "'");
                sb.append(",'name': '" + sku.getName() + "'");
                sb.append(",'sku': '" + sku.getId() + "'");
                sb.append(",'category': '" + getVariation(orderItem) + "'");
                sb.append(",'price': '" + orderItem.getAveragePrice() + "'");
                sb.append(",'quantity': '" + orderItem.getQuantity() + "'");
                sb.append("});");
            }
        }
        return sb.toString();
    }

    protected String getVariation(OrderItem item) {
        if (MapUtils.isEmpty(item.getOrderItemAttributes())) {
            return item.getCategory() == null ? "" : item.getCategory().getName();
        }
        
        //use product options instead
        String result = "";
        for (Map.Entry<String, OrderItemAttribute> entry : item.getOrderItemAttributes().entrySet()) {
            result += entry.getValue().getValue() + " ";
        }

        //the result has a space at the end, ensure that is stripped out
        return result.substring(0, result.length() - 1);
    }

    public String getMasterWebPropertyId() {
        return masterWebPropertyId;
    }

    public void setMasterWebPropertyId(String masterWebPropertyId) {
        this.masterWebPropertyId = masterWebPropertyId;
    }
    
    public String getAffiliation() {
        return BLCSystemProperty.resolveSystemProperty("googleAnalytics.affiliation");
    }
    
    public String getWebPropertyId() {
        return BLCSystemProperty.resolveSystemProperty("googleAnalytics.webPropertyId");
    }
    
    public boolean isIncludeLinkAttribution() {
        return BLCSystemProperty.resolveBooleanSystemProperty("googleAnalytics.enableLinkAttribution", true);
    }

    public boolean isIncludeDisplayAdvertising() {
        return BLCSystemProperty.resolveBooleanSystemProperty("googleAnalytics.enableDisplayAdvertising", false);
    }

}

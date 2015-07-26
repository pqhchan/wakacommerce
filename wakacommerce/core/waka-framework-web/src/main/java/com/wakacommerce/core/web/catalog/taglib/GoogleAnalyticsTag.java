
package com.wakacommerce.core.web.catalog.taglib;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.FulfillmentGroup;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.profile.core.domain.Address;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class GoogleAnalyticsTag extends SimpleTagSupport {
    
    private static final Log LOG = LogFactory.getLog(GoogleAnalyticsTag.class);

    protected String webPropertyId;
    
    protected String getWebPropertyIdDefault() {
        return BLCSystemProperty.resolveSystemProperty("googleAnalytics.webPropertyId");
    }

    private Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getWebPropertyId() {
        if (this.webPropertyId == null) {
            return getWebPropertyIdDefault();
        } else {
            return this.webPropertyId;
        }
    }

    public void setWebPropertyId(String webPropertyId) {
        this.webPropertyId = webPropertyId;
    }

    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();
        
        if (this.webPropertyId == null) {
            ServletContext sc = ((PageContext) getJspContext()).getServletContext();
            ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sc);
            context.getAutowireCapableBeanFactory().autowireBeanProperties(this, AutowireCapableBeanFactory.AUTOWIRE_BY_NAME, false);
        }
        
        String webPropertyId = getWebPropertyId();
        
        if (webPropertyId.equals("UA-XXXXXXX-X")) {
            LOG.warn("googleAnalytics.webPropertyId has not been overridden in a custom property file. Please set this in order to properly use the Google Analytics tag");
        }
        
        out.println(analytics(webPropertyId, order));
        super.doTag();
    }

    /**
     * Documentation for the recommended asynchronous GA tag is at:
     * http://code.google.com/apis/analytics/docs/tracking/gaTrackingEcommerce.html
     * 
     * @param webPropertyId - Google Analytics ID
     * @param order - optionally track the order submission. This should be included on the
     * page after the order has been sucessfully submitted. If null, this will just track the current page
     * @return the relevant Javascript to render on the page
     */
    protected String analytics(String webPropertyId, Order order) {
        StringBuffer sb = new StringBuffer();
        
        sb.append("<script type=\"text/javascript\">");
        sb.append("var _gaq = _gaq || [];");
        sb.append("_gaq.push(['_setAccount', '" + webPropertyId + "']);");
        sb.append("_gaq.push(['_trackPageview']);");
        
        if (order != null) {
            Address paymentAddress = null;
            for (OrderPayment payment : order.getPayments())  {
                if (payment.isActive() && PaymentType.CREDIT_CARD.equals(payment.getType())) {
                    paymentAddress = payment.getBillingAddress();
                }
            }

            sb.append("_gaq.push(['_addTrans','" + order.getId() + "'");
            sb.append(",'" + order.getName() + "'");
            sb.append(",'" + order.getTotal() + "'");
            sb.append(",'" + order.getTotalTax() + "'");
            sb.append(",'" + order.getTotalShipping() + "'");

            if (paymentAddress != null) {
                String state = null;
                if (StringUtils.isNotBlank(paymentAddress.getStateProvinceRegion())) {
                    state = paymentAddress.getStateProvinceRegion();
                } else if (paymentAddress.getState() != null) {
                    state = paymentAddress.getState().getName();
                }

                String country = null;
                if (paymentAddress.getIsoCountryAlpha2() != null) {
                    country = paymentAddress.getIsoCountryAlpha2().getName();
                } else if (paymentAddress.getCountry() != null) {
                    country = paymentAddress.getCountry().getName();
                }

                sb.append(",'" + paymentAddress.getCity() + "'");

                if (state != null) {
                    sb.append(",'" + state + "'");
                }

                if (country != null) {
                    sb.append(",'" + country + "'");
                }
            }
            sb.append("]);");

            for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
                for (FulfillmentGroupItem fulfillmentGroupItem : fulfillmentGroup.getFulfillmentGroupItems()) {
                    DiscreteOrderItem orderItem = (DiscreteOrderItem) fulfillmentGroupItem.getOrderItem();
                    sb.append("_gaq.push(['_addItem','" + order.getId() + "'");
                    sb.append(",'" + orderItem.getSku().getId() + "'");
                    sb.append(",'" + orderItem.getSku().getName() + "'");
                    sb.append(",' " + orderItem.getProduct().getDefaultCategory() + "'");
                    sb.append(",'" + orderItem.getPrice() + "'");
                    sb.append(",'" + orderItem.getQuantity() + "'");
                    sb.append("]);");
                }
            }
            sb.append("_gaq.push(['_trackTrans']);");
        }
        
        sb.append(" (function() {"
                + "var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;"
                + "ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';"
                + "var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);"
                + "})();");
        sb.append("</script>");

        return sb.toString();
    }
    
}

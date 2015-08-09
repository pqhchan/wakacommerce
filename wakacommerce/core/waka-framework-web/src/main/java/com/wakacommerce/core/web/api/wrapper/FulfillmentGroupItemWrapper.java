
package com.wakacommerce.core.web.api.wrapper;

import org.springframework.context.ApplicationContext;

import com.wakacommerce.common.money.Money;
import com.wakacommerce.core.order.domain.FulfillmentGroupItem;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.TaxDetail;
import com.wakacommerce.core.order.service.OrderItemService;
import com.wakacommerce.core.order.service.call.FulfillmentGroupItemRequest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "fulfillmentGroupItem")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class FulfillmentGroupItemWrapper extends BaseWrapper implements APIWrapper<FulfillmentGroupItem>, APIUnwrapper<FulfillmentGroupItemRequest> {

    @XmlElement
    protected Long id;

    @XmlElement
    protected Long fulfillmentGroupId;

    @XmlElement
    protected Long orderItemId;

    @XmlElement
    protected Money totalTax;

    @XmlElement
    protected Integer quantity;

    @XmlElement
    protected Money totalItemAmount;

    @XmlElement(name = "taxDetail")
    @XmlElementWrapper(name = "taxDetails")
    protected List<TaxDetailWrapper> taxDetails;

    @Override
    public void wrapDetails(FulfillmentGroupItem model, HttpServletRequest request) {
        this.id = model.getId();

        if (model.getFulfillmentGroup() != null) {
            this.fulfillmentGroupId = model.getFulfillmentGroup().getId();
        }

        if (model.getOrderItem() != null) {
            this.orderItemId = model.getOrderItem().getId();
        }

        this.totalTax = model.getTotalTax();
        this.quantity = model.getQuantity();
        this.totalItemAmount = model.getTotalItemAmount();

        List<TaxDetail> taxes = model.getTaxes();
        if (taxes != null && !taxes.isEmpty()) {
            this.taxDetails = new ArrayList<TaxDetailWrapper>();
            for (TaxDetail detail : taxes) {
                TaxDetailWrapper taxDetailWrapper = (TaxDetailWrapper) context.getBean(TaxDetailWrapper.class.getName());
                taxDetailWrapper.wrapSummary(detail, request);
                this.taxDetails.add(taxDetailWrapper);
            }
        }
    }

    @Override
    public void wrapSummary(FulfillmentGroupItem model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    @Override
    public FulfillmentGroupItemRequest unwrap(HttpServletRequest request, ApplicationContext appContext) {
        OrderItemService orderItemService = (OrderItemService) appContext.getBean("blOrderItemService");
        OrderItem orderItem = orderItemService.readOrderItemById(this.orderItemId);
        if (orderItem != null) {
            FulfillmentGroupItemRequest fulfillmentGroupItemRequest = new FulfillmentGroupItemRequest();
            fulfillmentGroupItemRequest.setOrderItem(orderItem);
            fulfillmentGroupItemRequest.setQuantity(this.quantity);
            return fulfillmentGroupItemRequest;
        }

        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFulfillmentGroupId() {
        return fulfillmentGroupId;
    }

    public void setFulfillmentGroupId(Long fulfillmentGroupId) {
        this.fulfillmentGroupId = fulfillmentGroupId;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Money getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(Money totalTax) {
        this.totalTax = totalTax;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Money getTotalItemAmount() {
        return totalItemAmount;
    }

    public void setTotalItemAmount(Money totalItemAmount) {
        this.totalItemAmount = totalItemAmount;
    }

    public List<TaxDetailWrapper> getTaxDetails() {
        return taxDetails;
    }

    public void setTaxDetails(List<TaxDetailWrapper> taxDetails) {
        this.taxDetails = taxDetails;
    }
}

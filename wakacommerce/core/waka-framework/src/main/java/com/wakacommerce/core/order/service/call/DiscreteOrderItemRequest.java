
package com.wakacommerce.core.order.service.call;

import java.util.ArrayList;
import java.util.List;

import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.DiscreteOrderItemFeePrice;
public class DiscreteOrderItemRequest extends AbstractOrderItemRequest {

    protected BundleOrderItem bundleOrderItem;
    
    protected List<DiscreteOrderItemFeePrice> discreteOrderItemFeePrices = new ArrayList<DiscreteOrderItemFeePrice>();

    public DiscreteOrderItemRequest() {
        super();
    }

    public DiscreteOrderItemRequest(AbstractOrderItemRequest request) {
        setCategory(request.getCategory());
        setItemAttributes(request.getItemAttributes());
        setPersonalMessage(request.getPersonalMessage());
        setProduct(request.getProduct());
        setQuantity(request.getQuantity());
        setSku(request.getSku());
        setOrder(request.getOrder());
        setSalePriceOverride(request.getSalePriceOverride());
        setRetailPriceOverride(request.getRetailPriceOverride());
    }


    @Override
    public DiscreteOrderItemRequest clone() {
        DiscreteOrderItemRequest returnRequest = new DiscreteOrderItemRequest();
        copyProperties(returnRequest);
        returnRequest.setDiscreteOrderItemFeePrices(discreteOrderItemFeePrices);
        return returnRequest;
    }

    public BundleOrderItem getBundleOrderItem() {
        return bundleOrderItem;
    }
    
    public void setBundleOrderItem(BundleOrderItem bundleOrderItem) {
        this.bundleOrderItem = bundleOrderItem;
    }

    public List<DiscreteOrderItemFeePrice> getDiscreteOrderItemFeePrices() {
        return discreteOrderItemFeePrices;
    }

    public void setDiscreteOrderItemFeePrices(
            List<DiscreteOrderItemFeePrice> discreteOrderItemFeePrices) {
        this.discreteOrderItemFeePrices = discreteOrderItemFeePrices;
    }
}

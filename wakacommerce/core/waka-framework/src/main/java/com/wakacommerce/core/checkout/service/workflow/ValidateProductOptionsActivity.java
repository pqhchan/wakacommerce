
package com.wakacommerce.core.checkout.service.workflow;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import com.wakacommerce.core.catalog.domain.Product;
import com.wakacommerce.core.catalog.domain.ProductOption;
import com.wakacommerce.core.catalog.service.type.ProductOptionValidationStrategyType;
import com.wakacommerce.core.order.domain.BundleOrderItem;
import com.wakacommerce.core.order.domain.DiscreteOrderItem;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderItem;
import com.wakacommerce.core.order.domain.OrderItemAttribute;
import com.wakacommerce.core.order.service.ProductOptionValidationService;
import com.wakacommerce.core.order.service.call.ActivityMessageDTO;
import com.wakacommerce.core.order.service.exception.ProductOptionValidationException;
import com.wakacommerce.core.order.service.exception.RequiredAttributeNotProvidedException;
import com.wakacommerce.core.order.service.type.MessageType;
import com.wakacommerce.core.workflow.ActivityMessages;
import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * This is an required activity to valiate if required product options are in the order.
 * 
 * If sku browsing is enabled, product option data will not be available.
 * In this case, the following validation is skipped.
 * 
 *Priyesh Patel
 *
 */
public class ValidateProductOptionsActivity extends BaseActivity<ProcessContext<CheckoutSeed>> {

    @Value("${solr.index.use.sku}")
    protected boolean useSku;

    @Resource(name = "blProductOptionValidationService")
    protected ProductOptionValidationService productOptionValidationService;


    @Override
    public ProcessContext<CheckoutSeed> execute(ProcessContext<CheckoutSeed> context) throws Exception {
        if(useSku) {
            return context;
        }
        Order order = context.getSeedData().getOrder();
        List<DiscreteOrderItem> orderItems = new ArrayList<DiscreteOrderItem>();
        for (OrderItem i : order.getOrderItems()) {
            if (i instanceof DiscreteOrderItem) {
                orderItems.add((DiscreteOrderItem) i);
            } else if (i instanceof BundleOrderItem) {
                orderItems.addAll(((BundleOrderItem) i).getDiscreteOrderItems());
            } else
                continue;
        }
        for (DiscreteOrderItem i : orderItems) {
            Map<String, OrderItemAttribute> attributeValues = i.getOrderItemAttributes();
            Product product = i.getProduct();

            if (product != null && product.getProductOptions() != null && product.getProductOptions().size() > 0) {
                for (ProductOption productOption : product.getProductOptions()) {
                    if (productOption.getRequired() && (productOption.getProductOptionValidationStrategyType() == null ||
                            productOption.getProductOptionValidationStrategyType().getRank() <= getProductOptionValidationStrategyType().getRank())) {
                        if (attributeValues.get(productOption.getAttributeName()) == null || StringUtils.isEmpty(attributeValues.get(productOption.getAttributeName()).getValue())) {
                            throw new RequiredAttributeNotProvidedException("Unable to validate cart, product  (" + product.getId() + ") required attribute was not provided: " + productOption.getAttributeName(), productOption.getAttributeName());
                        }
                    }
                    if (productOption.getProductOptionValidationType() != null && (productOption.getProductOptionValidationStrategyType() == null || productOption.getProductOptionValidationStrategyType().getRank() <= getProductOptionValidationStrategyType().getRank())) {
                        productOptionValidationService.validate(productOption, attributeValues.get(productOption.getAttributeName()).getValue());
                    }
                    if ((productOption.getProductOptionValidationStrategyType() != null && productOption.getProductOptionValidationStrategyType().getRank() > getProductOptionValidationStrategyType().getRank()))
                    {
                        //we need to validate however, we will not error out since this message is 
                        try {
                            productOptionValidationService.validate(productOption, (attributeValues.get(productOption.getAttributeName()) != null) ? attributeValues.get(productOption.getAttributeName()).getValue() : null);
                        } catch (ProductOptionValidationException e) {
                            ActivityMessageDTO msg = new ActivityMessageDTO(MessageType.PRODUCT_OPTION.getType(), 1, e.getMessage());
                            msg.setErrorCode(productOption.getErrorCode());
                            ((ActivityMessages) context).getActivityMessages().add(msg);
                        }

                    }
                }

            }
        }
        return context;
    }

    public ProductOptionValidationStrategyType getProductOptionValidationStrategyType() {
        return ProductOptionValidationStrategyType.SUBMIT_ORDER;
    }


}

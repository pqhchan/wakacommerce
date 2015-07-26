
package com.wakacommerce.core.order.dao;

import java.util.List;

import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.service.type.FulfillmentType;

/**
 * 
 *Phillip Verheyden
 */
public interface FulfillmentOptionDao {

    public FulfillmentOption readFulfillmentOptionById(final Long fulfillmentOptionId);

    public FulfillmentOption save(FulfillmentOption option);
    
    public List<FulfillmentOption> readAllFulfillmentOptions();

    public List<FulfillmentOption> readAllFulfillmentOptionsByFulfillmentType(FulfillmentType type);

}
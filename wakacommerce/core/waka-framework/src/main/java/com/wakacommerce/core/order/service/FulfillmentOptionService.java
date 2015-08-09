
package com.wakacommerce.core.order.service;

import java.util.List;

import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.service.type.FulfillmentType;

/**
 *
 * @ hui
 */
public interface FulfillmentOptionService {

    public FulfillmentOption readFulfillmentOptionById(Long fulfillmentOptionId);

    public FulfillmentOption save(FulfillmentOption option);

    public List<FulfillmentOption> readAllFulfillmentOptions();

    public List<FulfillmentOption> readAllFulfillmentOptionsByFulfillmentType(FulfillmentType type);

}

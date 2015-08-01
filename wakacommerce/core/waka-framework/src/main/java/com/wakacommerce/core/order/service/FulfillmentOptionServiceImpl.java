
package com.wakacommerce.core.order.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.core.order.dao.FulfillmentOptionDao;
import com.wakacommerce.core.order.domain.FulfillmentOption;
import com.wakacommerce.core.order.service.type.FulfillmentType;

import java.util.List;

import javax.annotation.Resource;

/**
 * 
 *  
 */
@Service("blFulfillmentOptionService")
public class FulfillmentOptionServiceImpl implements FulfillmentOptionService {

    @Resource(name = "blFulfillmentOptionDao")
    protected FulfillmentOptionDao fulfillmentOptionDao;

    @Override
    public FulfillmentOption readFulfillmentOptionById(Long fulfillmentOptionId) {
        return fulfillmentOptionDao.readFulfillmentOptionById(fulfillmentOptionId);
    }

    @Override
    @Transactional("blTransactionManager")
    public FulfillmentOption save(FulfillmentOption option) {
        return fulfillmentOptionDao.save(option);
    }

    @Override
    public List<FulfillmentOption> readAllFulfillmentOptions() {
        return fulfillmentOptionDao.readAllFulfillmentOptions();
    }

    @Override
    public List<FulfillmentOption> readAllFulfillmentOptionsByFulfillmentType(FulfillmentType type) {
        return fulfillmentOptionDao.readAllFulfillmentOptionsByFulfillmentType(type);
    }
}

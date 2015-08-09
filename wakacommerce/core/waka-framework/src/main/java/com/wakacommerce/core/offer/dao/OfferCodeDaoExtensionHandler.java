
package com.wakacommerce.core.offer.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

public interface OfferCodeDaoExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType createReadOfferCodeByCodeQuery(EntityManager em,
            ExtensionResultHolder<Query> resultHolder, String code, boolean cacheable, String cacheRegion);

}

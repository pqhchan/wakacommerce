
package com.wakacommerce.core.offer.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

/**
 *
 * @ hui
 */
public class DefaultOfferCodeDaoExtensionHandler extends AbstractExtensionHandler implements OfferCodeDaoExtensionHandler {

    @Override
    public ExtensionResultStatusType createReadOfferCodeByCodeQuery(EntityManager em, ExtensionResultHolder<Query> resultHolder, String code, boolean cacheable, String cacheRegion) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}


package com.wakacommerce.common.i18n.dao;

import javax.persistence.EntityManager;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultHolder;
import com.wakacommerce.common.extension.ExtensionResultStatusType;


/**
 *
 * @ hui
 */
public interface TranslationDaoExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType overrideRequestedId(ExtensionResultHolder erh, EntityManager em, 
            Class<?> clazz, Long entityId);

}

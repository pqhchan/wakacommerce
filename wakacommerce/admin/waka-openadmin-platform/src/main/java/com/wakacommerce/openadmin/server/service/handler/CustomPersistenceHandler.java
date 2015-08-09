
package com.wakacommerce.openadmin.server.service.handler;

import org.springframework.core.Ordered;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.dao.DynamicEntityDao;
import com.wakacommerce.openadmin.server.service.persistence.module.InspectHelper;
import com.wakacommerce.openadmin.server.service.persistence.module.RecordHelper;

/**
 *
 * @ hui
 */
public interface CustomPersistenceHandler extends Ordered {

    public static final int DEFAULT_ORDER = Integer.MAX_VALUE;

    public Boolean canHandleInspect(PersistencePackage persistencePackage);

    public Boolean canHandleFetch(PersistencePackage persistencePackage);

    public Boolean canHandleAdd(PersistencePackage persistencePackage);

    public Boolean canHandleRemove(PersistencePackage persistencePackage);

    public Boolean canHandleUpdate(PersistencePackage persistencePackage);
    public Boolean willHandleSecurity(PersistencePackage persistencePackage);
    
    public DynamicResultSet inspect(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, InspectHelper helper) throws ServiceException;

    public DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException;
    
    public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException;
    
    public void remove(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException;
    
    public Entity update(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException;
    
}

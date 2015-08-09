
package com.wakacommerce.openadmin.server.service;

import org.springframework.security.access.annotation.Secured;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceResponse;

/**
 *
 * @ hui
 */
public interface DynamicEntityService {

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse inspect(PersistencePackage persistencePackage) throws ServiceException;

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto) throws ServiceException;

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse add(PersistencePackage persistencePackage) throws ServiceException;

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse nonTransactionalAdd(PersistencePackage persistencePackage) throws ServiceException;

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse update(PersistencePackage persistencePackage) throws ServiceException;

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse nonTransactionalUpdate(PersistencePackage persistencePackage) throws ServiceException;

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse remove(PersistencePackage persistencePackage) throws ServiceException;

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse nonTransactionalRemove(PersistencePackage persistencePackage) throws ServiceException;
    
}

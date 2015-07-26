
package com.wakacommerce.openadmin.server.service;

import org.springframework.security.access.annotation.Secured;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceResponse;

/**
 * Rather than using this clas directly, it might be more appropraite to utilize {@link AdminEntityService} instead. The
 * methods in this class will not attempt to recover from things like validation problems whereas {@link AdminEntityService}
 * will.
 * 
 *jfischer
 */
public interface DynamicEntityService {
    
    /**
     * Builds all of the metadata associated with a particular request for an entity. The resulting {@link PersistenceResponse}
     * that is returned will not have the {@link PersistenceResponse#getEntity()} property set and this will return null.
     * Instead, this will populate {@link PersistenceResponse#getDynamicResultSet()}.
     * 
     * @param persistencePackage the package that should be passed through the admin pipeline to build the metadata
     * @return a {@link PersistenceResponse} with the {@link PersistenceResponse#getDynamicResultSet()} set with the 
     * metadata of the built properties for this particular entity
     * @throws ServiceException wraps whatever internal exception that might have occurred as a result of the inspect
     */
    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse inspect(PersistencePackage persistencePackage) throws ServiceException;

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto) throws ServiceException;
    
    /**
     * This will throw a {@link ValidationException} and not attempt to swallow them and wrap any other exceptions within
     * a {@link ServiceException} that might have resulted in adding the given package.
     * 
     * @param persistencePackage
     * @return
     * @throws ServiceException
     * @see {@link AdminEntityService#add(com.wakacommerce.openadmin.server.domain.PersistencePackageRequest)}
     */
    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse add(PersistencePackage persistencePackage) throws ServiceException;

    /**
     * The exact same as {@link #add(PersistencePackage)} except this is not bound to a transaction. This is useful when
     * transactions are handled by the caller that has its own rollback logic (like when batching multiple adds).
     * 
     * @param persistencePackage
     * @return
     * @throws ServiceException
     */
    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse nonTransactionalAdd(PersistencePackage persistencePackage) throws ServiceException;

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse update(PersistencePackage persistencePackage) throws ServiceException;

    /**
     * The exact same as {@link #update(PersistencePackage)} except this is not bound to a transaction. This is useful when
     * transactions are handled by the caller that has its own rollback logic (like when batching multiple updates).
     * 
     * @param persistencePackage
     * @return
     * @throws ServiceException
     */
    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse nonTransactionalUpdate(PersistencePackage persistencePackage) throws ServiceException;

    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse remove(PersistencePackage persistencePackage) throws ServiceException;
    
    /**
     * The exact same as {@link #remove(PersistencePackage)} except this is not bound to a transaction. This is useful when
     * transactions are handled by the caller that has its own rollback logic (like when batching multiple removes).
     * 
     * @param persistencePackage
     * @return
     * @throws ServiceException
     */
    @Secured("PERMISSION_OTHER_DEFAULT")
    public PersistenceResponse nonTransactionalRemove(PersistencePackage persistencePackage) throws ServiceException;
    
}

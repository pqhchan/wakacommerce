
package com.wakacommerce.openadmin.server.security.remote;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.PersistencePackage;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;

/**
 *Jeff Fischer
 */
public interface SecurityVerifier {

    AdminUser getPersistentAdminUser();

    void securityCheck(String ceilingEntityFullyQualifiedName, EntityOperationType operationType) throws ServiceException;

    void securityCheck(PersistencePackage persistencePackage, EntityOperationType operationType) throws ServiceException;

}

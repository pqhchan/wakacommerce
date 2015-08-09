
package com.wakacommerce.openadmin.server.security.remote;

import com.wakacommerce.common.exception.ServiceException;

/**
 *
 * @ hui
 */
public interface AdminSecurityService {

    public AdminUser getAdminUser() throws ServiceException;
    
}

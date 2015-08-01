
package com.wakacommerce.openadmin.server.security.remote;

import com.wakacommerce.common.exception.ServiceException;

/**
 * 
 *  
 *
 */
public interface AdminSecurityService {

    public AdminUser getAdminUser() throws ServiceException;
    
}

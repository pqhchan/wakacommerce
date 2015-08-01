
package com.wakacommerce.openadmin.server.security.service;

import com.wakacommerce.common.security.BroadleafExternalAuthenticationUserDetails;

/**
 * Utility to add or update the AdminUser object in the database after authentication from an external system.
 * 
 *  
 *
 */
public interface AdminUserProvisioningService {

    /**
     * This method uses the details argument to add or update an AdminUser object in the database, 
     * along with appropriate roles and permissions.  The result of the call to this should be an instance of 
     * AdminUserDetails.
     * 
     * NOTE: IT IS GENERALLY EXPECTED THAT THIS METHOD WILL BE CALLED AFTER A USER HAS BEEN AUTHENTICATED.
     * 
     * @param details
     * @return
     */
    public AdminUserDetails provisionAdminUser(BroadleafExternalAuthenticationUserDetails details);

}

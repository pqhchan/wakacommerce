
package com.wakacommerce.openadmin.server.security.service;

import com.wakacommerce.common.security.BroadleafExternalAuthenticationUserDetails;

/**
 *
 * @ hui
 */
public interface AdminUserProvisioningService {

    public AdminUserDetails provisionAdminUser(BroadleafExternalAuthenticationUserDetails details);

}

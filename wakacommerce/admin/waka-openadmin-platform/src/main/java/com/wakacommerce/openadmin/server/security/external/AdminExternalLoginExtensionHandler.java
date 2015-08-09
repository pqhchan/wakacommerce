  
package com.wakacommerce.openadmin.server.security.external;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.security.BroadleafExternalAuthenticationUserDetails;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;

public interface AdminExternalLoginExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType performAdditionalAuthenticationTasks(
            AdminUser adminUser, BroadleafExternalAuthenticationUserDetails details);
    

}

  
package com.wakacommerce.openadmin.server.security.external;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.security.BroadleafExternalAuthenticationUserDetails;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;

public interface AdminExternalLoginExtensionHandler extends ExtensionHandler {

    /**
     * This can be used to associate, for example, Site to the adminUser, or to validate that the Site that the adminUser 
     * has access to is the current site.  Implementors may also wish to assign additional data to the admin user, persist 
     * custom data, validate additional access rules, etc.
     * Implementors should not persist the adminUser object. Rather modify or augment the state of the adminUser object 
     * only.  Persistence should be handled outside of this.
     * 
     * If an implementor decides that the user is not actually authenticated or should not be allowed access, an instance of  
     * <code>org.springframework.security.core.AuthenticationException</code> should be thrown.
     * 
     * @param adminUser
     * @param details
     * @return
     */
    public ExtensionResultStatusType performAdditionalAuthenticationTasks(
            AdminUser adminUser, BroadleafExternalAuthenticationUserDetails details);
    

}

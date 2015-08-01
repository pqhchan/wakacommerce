
package com.wakacommerce.openadmin.server.security.service.navigation;

import com.wakacommerce.openadmin.server.security.domain.AdminSection;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;

/**
 * 
 */
public interface SectionAuthorization {
    boolean isUserAuthorizedToViewSection(AdminUser adminUser, AdminSection section);
}

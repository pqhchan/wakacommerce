
package com.wakacommerce.openadmin.server.security.service.navigation;

import java.util.List;

import com.wakacommerce.openadmin.server.security.domain.AdminMenu;
import com.wakacommerce.openadmin.server.security.domain.AdminModule;
import com.wakacommerce.openadmin.server.security.domain.AdminSection;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;

public interface AdminNavigationService {

    public AdminMenu buildMenu(AdminUser adminUser);

    public boolean isUserAuthorizedToViewSection(AdminUser adminUser, AdminSection section);

    public boolean isUserAuthorizedToViewModule(AdminUser adminUser, AdminModule module);

    public AdminSection findAdminSectionByURI(String uri);

    public AdminSection findAdminSectionBySectionKey(String sectionKey);

    AdminSection findAdminSectionByClassAndSectionId(String className, String sectionId);

    AdminSection findAdminSectionByClassAndSectionId(Class<?> clazz, String sectionId);

    public List<AdminSection> findAllAdminSections();

    public AdminSection save(AdminSection adminSection);

    public void remove(AdminSection adminSection);

}

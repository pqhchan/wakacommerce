
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

    /**
     * In some cases, a single class is served by more than one section.
     * 
     * @param className
     * @param sectionId
     * @return
     */
    AdminSection findAdminSectionByClassAndSectionId(String className, String sectionId);

    /**
     * In some cases, a single class is served by more than one section.
     * 
     * @param className
     * @param sectionId
     * @return
     */
    AdminSection findAdminSectionByClassAndSectionId(Class<?> clazz, String sectionId);

    /**
     * Gets all of the {@link AdminSection}s in the system, sorted by the {@link AdminSection#getDisplayOrder()}
     * @return the list of all {@link AdminSection}s sorted by {@link AdminSection#getDisplayOrder()}
     */
    public List<AdminSection> findAllAdminSections();

    public AdminSection save(AdminSection adminSection);

    public void remove(AdminSection adminSection);

}

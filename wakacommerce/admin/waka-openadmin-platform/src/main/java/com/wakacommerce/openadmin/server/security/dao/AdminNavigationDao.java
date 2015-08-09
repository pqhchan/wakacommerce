
package com.wakacommerce.openadmin.server.security.dao;


import java.util.List;

import com.wakacommerce.openadmin.server.security.domain.AdminModule;
import com.wakacommerce.openadmin.server.security.domain.AdminSection;

/**
 *
 * @ hui
 */
public interface AdminNavigationDao {

    public List<AdminModule> readAllAdminModules();

    public List<AdminSection> readAllAdminSections();
    
    public AdminSection readAdminSectionByClassAndSectionId(Class<?> clazz, String sectionId);

    public AdminSection readAdminSectionByURI(String uri);

    public AdminSection readAdminSectionBySectionKey(String sectionKey);

    public AdminSection save(AdminSection adminSection);

    public void remove(AdminSection adminSection);

    public AdminModule readAdminModuleByModuleKey(String moduleKey);

}

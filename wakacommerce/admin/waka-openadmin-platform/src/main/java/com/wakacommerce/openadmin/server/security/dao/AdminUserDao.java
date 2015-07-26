
package com.wakacommerce.openadmin.server.security.dao;

import java.util.List;
import java.util.Set;

import com.wakacommerce.openadmin.server.security.domain.AdminUser;

/**
 * 
 *jfischer
 *
 */
public interface AdminUserDao {
    List<AdminUser> readAllAdminUsers();
    AdminUser readAdminUserById(Long id);
    AdminUser readAdminUserByUserName(String userName);
    AdminUser saveAdminUser(AdminUser user);
    void deleteAdminUser(AdminUser user);
    List<AdminUser> readAdminUserByEmail(String emailAddress);
    List<AdminUser> readAdminUsersByIds(Set<Long> ids);
}


package com.wakacommerce.openadmin.server.security.dao;

import java.util.List;

import com.wakacommerce.openadmin.server.security.domain.AdminRole;

/**
 *
 * @ hui
 */
public interface AdminRoleDao {
    public List<AdminRole> readAllAdminRoles();
    public AdminRole readAdminRoleById(Long id);
    public AdminRole saveAdminRole(AdminRole role);
    public void deleteAdminRole(AdminRole role);
}

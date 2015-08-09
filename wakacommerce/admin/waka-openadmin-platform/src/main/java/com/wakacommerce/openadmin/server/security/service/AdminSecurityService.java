
package com.wakacommerce.openadmin.server.security.service;

import org.springframework.security.authentication.dao.SaltSource;

import com.wakacommerce.common.security.util.PasswordChange;
import com.wakacommerce.common.service.GenericResponse;
import com.wakacommerce.openadmin.server.security.domain.AdminPermission;
import com.wakacommerce.openadmin.server.security.domain.AdminRole;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;
import com.wakacommerce.openadmin.server.security.service.type.PermissionType;

import java.util.List;

/**
 *
 * @ hui
 */
public interface AdminSecurityService {

    public static final String[] DEFAULT_PERMISSIONS = { "PERMISSION_OTHER_DEFAULT", "PERMISSION_ALL_USER_SANDBOX" };

    List<AdminUser> readAllAdminUsers();
    AdminUser readAdminUserById(Long id);
    AdminUser readAdminUserByUserName(String userName);
    AdminUser saveAdminUser(AdminUser user);
    void deleteAdminUser(AdminUser user);

    List<AdminRole> readAllAdminRoles();
    AdminRole readAdminRoleById(Long id);
    AdminRole saveAdminRole(AdminRole role);
    void deleteAdminRole(AdminRole role);

    List<AdminPermission> readAllAdminPermissions();
    AdminPermission readAdminPermissionById(Long id);
    AdminPermission saveAdminPermission(AdminPermission permission);
    void deleteAdminPermission(AdminPermission permission);
    AdminUser changePassword(PasswordChange passwordChange);

    boolean isUserQualifiedForOperationOnCeilingEntity(AdminUser adminUser, PermissionType permissionType, String ceilingEntityFullyQualifiedName);
    boolean doesOperationExistForCeilingEntity(PermissionType permissionType, String ceilingEntityFullyQualifiedName);

    GenericResponse sendForgotUsernameNotification(String emailAddress);

    GenericResponse sendResetPasswordNotification(String userName);

    GenericResponse resetPasswordUsingToken(String username, String token, String password, String confirmPassword);

    GenericResponse changePassword(String username, String oldPassword, String password, String confirmPassword);

    @Deprecated
    public String getSalt();

    @Deprecated
    public void setSalt(String salt);

    @Deprecated
    public SaltSource getSaltSource();

    @Deprecated
    public void setSaltSource(SaltSource saltSource);

    @Deprecated
    public Object getSalt(AdminUser user, String unencodedPassword);

    public List<AdminUser> readAdminUsersByEmail(String email);

}

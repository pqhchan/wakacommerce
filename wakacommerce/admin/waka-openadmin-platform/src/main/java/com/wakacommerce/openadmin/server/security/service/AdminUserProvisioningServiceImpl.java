package com.wakacommerce.openadmin.server.security.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.security.BroadleafExternalAuthenticationUserDetails;
import com.wakacommerce.openadmin.server.security.domain.AdminPermission;
import com.wakacommerce.openadmin.server.security.domain.AdminRole;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;
import com.wakacommerce.openadmin.server.security.domain.AdminUserImpl;
import com.wakacommerce.openadmin.server.security.external.AdminExternalLoginUserExtensionManager;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Service("blAdminUserProvisioningService")
public class AdminUserProvisioningServiceImpl implements AdminUserProvisioningService {

    @Resource(name = "blAdminSecurityService")
    protected AdminSecurityService securityService;

    @Resource(name = "blAdminExternalLoginExtensionManager")
    protected AdminExternalLoginUserExtensionManager adminExternalLoginExtensionManager;

    protected Map<String, String[]> roleNameSubstitutions;

    @Override
    public AdminUserDetails provisionAdminUser(BroadleafExternalAuthenticationUserDetails details) {
        HashSet<String> newRoles = new HashSet<String>();

        if (roleNameSubstitutions != null && !roleNameSubstitutions.isEmpty()) {
            for (GrantedAuthority authority : details.getAuthorities()) {
                if (roleNameSubstitutions.containsKey(authority.getAuthority())) {
                    String[] roles = roleNameSubstitutions.get(authority.getAuthority());
                    for (String role : roles) {
                        newRoles.add(role.trim());
                    }
                } else {
                    newRoles.add(authority.getAuthority());
                }
            }
        } else {
            for (GrantedAuthority authority : details.getAuthorities()) {
                newRoles.add(authority.getAuthority());
            }
        }

        HashSet<GrantedAuthority> newAuthorities = new HashSet<GrantedAuthority>();
        for (String perm : AdminSecurityService.DEFAULT_PERMISSIONS) {
            newAuthorities.add(new SimpleGrantedAuthority(perm));
        }

        HashSet<AdminRole> grantedRoles = new HashSet<AdminRole>();
        List<AdminRole> adminRoles = securityService.readAllAdminRoles();
        if (adminRoles != null) {
            for (AdminRole role : adminRoles) {
                if (newRoles.contains(role.getName())) {
                    grantedRoles.add(role);
                    Set<AdminPermission> permissions = role.getAllPermissions();
                    if (permissions != null && !permissions.isEmpty()) {
                        for (AdminPermission permission : permissions) {
                            if (permission.isFriendly()) {
                                for (AdminPermission childPermission : permission.getAllChildPermissions()) {
                                    newAuthorities.add(new SimpleGrantedAuthority(childPermission.getName()));
                                }
                            } else {
                                newAuthorities.add(new SimpleGrantedAuthority(permission.getName()));
                            }
                        }
                    }
                }
            }
        }

        AdminUser adminUser = securityService.readAdminUserByUserName(details.getUsername());
        if (adminUser == null) {
            adminUser = new AdminUserImpl();
            adminUser.setLogin(details.getUsername());
        }

        if (StringUtils.isNotBlank(details.getEmail())) {
            adminUser.setEmail(details.getEmail());
        }

        StringBuilder name = new StringBuilder();
        if (StringUtils.isNotBlank(details.getFirstName())) {
            name.append(details.getFirstName()).append(" ");
        }
        if (StringUtils.isNotBlank(details.getLastName())) {
            name.append(details.getLastName());
        }

        String fullName = name.toString();
        if (StringUtils.isNotBlank(fullName)) {
            adminUser.setName(fullName);
        } else {
            adminUser.setName(details.getUsername());
        }

        //We have to do this because BLC replies on the role relationships being stored in the DB
        Set<AdminRole> roleSet = adminUser.getAllRoles();
        //First, remove all roles associated with the user if they already existed
        if (roleSet != null) {
            //First, remove all role relationships in case they have changed
            roleSet.clear();
        } else {
            roleSet = new HashSet<AdminRole>();
            adminUser.setAllRoles(roleSet);
        }

        //Now, add all of the role relationships back.
        if (adminRoles != null) {
            for (AdminRole role : adminRoles) {
                roleSet.add(role);
            }
        }

        //Add optional support for things like Multi-Tenant, etc...
        adminExternalLoginExtensionManager.getProxy().performAdditionalAuthenticationTasks(adminUser, details);

        //Save the user data and all of the roles...
        adminUser = securityService.saveAdminUser(adminUser);

        return new AdminUserDetails(adminUser.getId(), details.getUsername(), "", true, true, true, true, newAuthorities);
    }

    public void setRoleNameSubstitutions(Map<String, String[]> roleNameSubstitutions) {
        this.roleNameSubstitutions = roleNameSubstitutions;
    }
}

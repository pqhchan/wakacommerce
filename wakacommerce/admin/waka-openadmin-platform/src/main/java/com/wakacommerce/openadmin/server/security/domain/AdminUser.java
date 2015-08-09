
package com.wakacommerce.openadmin.server.security.domain;

import java.util.Map;
import java.util.Set;

import com.wakacommerce.common.sandbox.domain.SandBox;

/**
 *
 * @ hui
 */
public interface AdminUser extends AdminSecurityContext {
    public Long getId();
    public void setId(Long id);
    public String getName();
    public void setName(String name);
    public String getLogin();
    public void setLogin(String login);
    public String getPassword();
    public void setPassword(String password);
    public String getEmail();
    public void setEmail(String email);
    public Set<AdminRole> getAllRoles();
    public void setAllRoles(Set<AdminRole> allRoles);
    public String getUnencodedPassword();
    public void setUnencodedPassword(String unencodedPassword);

    public void setPhoneNumber(String phone);

    public String getPhoneNumber();

    public void setActiveStatusFlag(Boolean activeStatus);

    public Boolean getActiveStatusFlag();

    public SandBox getOverrideSandBox();

    public void setOverrideSandBox(SandBox sandbox);

    public Set<AdminPermission> getAllPermissions();
    public void setAllPermissions(Set<AdminPermission> allPermissions);
    //public AdminUser clone();

    public Map<String, String> getFlatAdditionalFields();

    public Map<String, AdminUserAttribute> getAdditionalFields();

    public void setAdditionalFields(Map<String, AdminUserAttribute> additionalFields);

    public Long getLastUsedSandBoxId();

    public void setLastUsedSandBoxId(Long sandBoxId);

}

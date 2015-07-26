
package com.wakacommerce.openadmin.server.security.domain;

import java.io.Serializable;
import java.util.Set;

import com.wakacommerce.openadmin.server.service.type.ContextType;

public interface AdminSecurityContext extends Serializable {

    public ContextType getContextType();

    public void setContextType(ContextType contextType);

    public String getContextKey();

    public void setContextKey(String contextKey);

    public Set<AdminRole> getAllRoles();

    public void setAllRoles(Set<AdminRole> allRoles);

    public Set<AdminPermission> getAllPermissions();

    public void setAllPermissions(Set<AdminPermission> allPermissions);

}


package com.wakacommerce.openadmin.server.security.domain;

/**
 *
 * @ hui
 */
public interface AdminPermissionQualifiedEntity {
    Long getId();

    void setId(Long id);

    String getCeilingEntityFullyQualifiedName();

    void setCeilingEntityFullyQualifiedName(String ceilingEntityFullyQualifiedName);

    public AdminPermission getAdminPermission();

    public void setAdminPermission(AdminPermission adminPermission);

    public AdminPermissionQualifiedEntity clone();
}

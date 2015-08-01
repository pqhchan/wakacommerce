
package com.wakacommerce.openadmin.server.security.domain;

import com.wakacommerce.common.value.ValueAssignable;

/**
 * Stores additional attributes for {@link AdminUser}s
 * 
 * 
 */
public interface AdminUserAttribute extends ValueAssignable<String> {

    /**
     * Returns the id
     * @return the id
     */
    public Long getId();

    /**
     * Sets the id
     * @param id
     */
    public void setId(Long id);

    /**
     * Returns the {@link AdminUser}
     * @return the AdminUser
     */
    public AdminUser getAdminUser();

    /**
     * Sets the {@link AdminUser}
     * @param adminUser
     */
    public void setAdminUser(AdminUser adminUser);

}

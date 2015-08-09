
package com.wakacommerce.openadmin.server.security.domain;

import com.wakacommerce.common.value.ValueAssignable;

/**
 *
 * @ hui
 */
public interface AdminUserAttribute extends ValueAssignable<String> {

    public Long getId();

    public void setId(Long id);

    public AdminUser getAdminUser();

    public void setAdminUser(AdminUser adminUser);

}


package com.wakacommerce.common.site.domain;

import java.io.Serializable;

/**
 *
 * @ hui
 */
public interface Theme extends Serializable {

    public Long getId();

    public void setId(Long id);

    public String getName();

    public void setName(String name);

    public String getPath();

    public void setPath(String path);
}

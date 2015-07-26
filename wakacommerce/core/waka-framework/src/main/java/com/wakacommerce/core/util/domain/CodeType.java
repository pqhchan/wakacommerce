
package com.wakacommerce.core.util.domain;

import java.io.Serializable;

@Deprecated
public interface CodeType extends Serializable {

    public void setId(Long id);

    public Long getId();

    public void setCodeType(String type);

    public String getCodeType();

    public void setKey(String key);

    public String getKey();

    public void setDescription(String description);

    public String getDescription();

    public void setModifiable(Boolean modifiable);

    public Boolean getModifiable();

    public Boolean isModifiable();

}

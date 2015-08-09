
package com.wakacommerce.common.sandbox.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.wakacommerce.common.persistence.Status;

public interface SandBox extends Serializable, Status {

    Long getId();

    void setId(Long id);

    String getName();

    void setName(String name);

    SandBoxType getSandBoxType();

    void setSandBoxType(SandBoxType sandBoxType);

    Long getAuthor();

    void setAuthor(Long author);

    SandBox getParentSandBox();

    void setParentSandBox(SandBox parentSandBox);

    String getColor();

    void setColor(String color);

    Date getGoLiveDate();

    void setGoLiveDate(Date goLiveDate);

    List<Long> getSandBoxIdsForUpwardHierarchy(boolean includeInherited);

    List<Long> getSandBoxIdsForUpwardHierarchy(boolean includeInherited, boolean includeCurrent);

    List<SandBox> getChildSandBoxes();

    void setChildSandBoxes(List<SandBox> childSandBoxes);

    public boolean getIsInDefaultHierarchy();

    public void setArchived(Character archived);

    public Character getArchived();

    public boolean isActive();

}



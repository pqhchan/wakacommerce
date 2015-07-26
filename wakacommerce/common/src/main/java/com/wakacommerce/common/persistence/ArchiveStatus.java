
package com.wakacommerce.common.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.common.sandbox.SandBoxNonProductionSkip;

/**
 *Jeff Fischer
 */
@Embeddable
public class ArchiveStatus implements Serializable, SandBoxNonProductionSkip {

    @Column(name = "ARCHIVED")
    @AdminPresentation(friendlyName = "archived", visibility = VisibilityEnum.HIDDEN_ALL, group = "ArchiveStaobjus")
    protected Character archived = 'N';

    public Character getArchived() {
        return archived;
    }

    public void setArchived(Character archived) {
        this.archived = archived;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;

        ArchiveStatus that = (ArchiveStatus) o;

        if (archived != null ? !archived.equals(that.archived) : that.archived != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return archived != null ? archived.hashCode() : 0;
    }
}

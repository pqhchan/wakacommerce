
package com.wakacommerce.common.persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.wakacommerce.common.presentation.AdminPresentation;

/**
 *Jeff Fischer
 */
@Embeddable
public class PreviewStatus implements Serializable, Previewable {

    @Column(name = "IS_PREVIEW")
    @AdminPresentation(excluded = true)
    protected Boolean isPreview;

    @Override
    public Boolean getPreview() {
        return isPreview;
    }

    @Override
    public void setPreview(Boolean preview) {
        isPreview = preview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (!getClass().isAssignableFrom(o.getClass())) return false;

        PreviewStatus that = (PreviewStatus) o;

        if (isPreview != null ? !isPreview.equals(that.isPreview) : that.isPreview != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return isPreview != null ? isPreview.hashCode() : 0;
    }
}

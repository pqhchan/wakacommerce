package com.wakacommerce.openadmin.web.form.component;

import com.wakacommerce.common.media.domain.Media;
import com.wakacommerce.openadmin.web.form.entity.Field;

public class MediaField extends Field {

    protected Media media;
    protected String height;

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

}

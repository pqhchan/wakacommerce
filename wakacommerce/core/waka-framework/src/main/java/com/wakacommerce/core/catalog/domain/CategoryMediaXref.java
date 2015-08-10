package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;

import com.wakacommerce.common.media.domain.Media;

/**
 *
 * @ hui
 */
public interface CategoryMediaXref extends Serializable {

    Long getId();

    void setId(Long id);

    Category getCategory();

    void setCategory(Category category);

    Media getMedia();

    void setMedia(Media media);

    String getKey();

    void setKey(String key);

}

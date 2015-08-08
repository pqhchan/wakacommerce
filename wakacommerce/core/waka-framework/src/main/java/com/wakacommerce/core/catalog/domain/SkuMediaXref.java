  
package com.wakacommerce.core.catalog.domain;

import java.io.Serializable;

import com.wakacommerce.common.media.domain.Media;

/**
 * 
 */
public interface SkuMediaXref extends Serializable{

    Long getId();

    void setId(Long id);

    Sku getSku();

    void setSku(Sku sku);

    Media getMedia();

    void setMedia(Media media);

    String getKey();

    void setKey(String key);

}

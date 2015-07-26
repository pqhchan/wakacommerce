
package com.wakacommerce.common.media.domain;

import java.io.Serializable;

import com.wakacommerce.common.util.Wrappable;

public interface Media extends Wrappable, Serializable {

    public Long getId();

    public void setId(Long id);

    public String getUrl();

    public void setUrl(String url);
    
    public String getTitle();

    public void setTitle(String title);

    public String getAltText();

    public void setAltText(String altText);
    
    public String getTags();

    public void setTags(String tags);

}

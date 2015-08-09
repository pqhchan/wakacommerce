
package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.wakacommerce.common.media.domain.Media;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "media")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class MediaWrapper extends BaseWrapper implements APIWrapper<Media> {

    @XmlTransient
    protected boolean allowOverrideUrl = true;

    @XmlElement
    protected Long id;

    @XmlElement
    protected String title;

    @XmlElement
    protected String url;

    @XmlElement
    protected String altText;
    
    @XmlElement
    protected String tags;
    
    @Override
    public void wrapDetails(Media media, HttpServletRequest request) {
        this.id = media.getId();
        this.title = media.getTitle();
        this.altText = media.getAltText();
        this.tags = media.getTags();
        this.url = media.getUrl();
    }

    @Override
    public void wrapSummary(Media media, HttpServletRequest request) {
        wrapDetails(media, request);
    }

    public boolean isAllowOverrideUrl() {
        return allowOverrideUrl;
    }

    public void setAllowOverrideUrl(boolean allow) {
        this.allowOverrideUrl = allow;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        if (allowOverrideUrl) {
            this.url = url;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

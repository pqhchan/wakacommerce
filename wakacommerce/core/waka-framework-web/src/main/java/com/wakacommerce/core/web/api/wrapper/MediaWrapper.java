
package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.wakacommerce.common.media.domain.Media;

/**
 *  JAXB wrapper class for Media.
 */
@XmlRootElement(name = "media")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class MediaWrapper extends BaseWrapper implements APIWrapper<Media> {

    /**
     * This allows us to control whether the URL should / can be overwritten, for example by the static asset service.
     */
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
    
    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }
    
    /**
     * Call this only if allowOverrideUrl is true, and only AFTER you call wrap.
     * @param url
     */
    public void setUrl(String url) {
        if (allowOverrideUrl) {
            this.url = url;
        }
    }

    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    
    /**
     * @return the altText
     */
    public String getAltText() {
        return altText;
    }

    
    /**
     * @param altText the altText to set
     */
    public void setAltText(String altText) {
        this.altText = altText;
    }

    
    /**
     * @return the tags
     */
    public String getTags() {
        return tags;
    }

    
    /**
     * @param tags the tags to set
     */
    public void setTags(String tags) {
        this.tags = tags;
    }
}

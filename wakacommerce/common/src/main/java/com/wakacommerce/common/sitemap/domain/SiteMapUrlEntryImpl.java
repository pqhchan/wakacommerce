

package com.wakacommerce.common.sitemap.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.sitemap.service.type.SiteMapChangeFreqType;
import com.wakacommerce.common.sitemap.service.type.SiteMapPriorityType;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 *  
 */
@Entity
@Table(name = "BLC_SITE_MAP_URL_ENTRY")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blConfigurationModuleElements")
@AdminPresentationClass(friendlyName = "SiteMapURLEntryImpl")
public class SiteMapUrlEntryImpl implements SiteMapUrlEntry {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator = "URLEntryId")
    @GenericGenerator(
            name = "URLEntryId",
            strategy = "com.wakacommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name = "segment_value", value = "SiteMapURLEntryImpl"),
                    @Parameter(name = "entity_name", value = "com.wakacommerce.common.sitemap.domain.SiteMapURLEntryImpl")
            })
    @Column(name = "URL_ENTRY_ID")
    protected Long id;

    @Column(name = "LOCATION", nullable = false)
    @AdminPresentation(friendlyName = "SiteMapURLEntryImpl_Location", gridOrder = 1, prominent = true)
    protected String location;
    
    @Column(name = "LAST_MODIFIED", nullable = false)
    @AdminPresentation(friendlyName = "SiteMapURLEntryImpl_Last_Modified", fieldType = SupportedFieldType.DATE, gridOrder = 2, prominent = true)
    protected Date lastModified = new Date();
    
    @Column(name = "CHANGE_FREQ", nullable = false)
    @AdminPresentation(friendlyName = "SiteMapURLEntryImpl_Change_Freq", fieldType = SupportedFieldType.WAKA_ENUMERATION,
            wakaEnumeration = "com.wakacommerce.common.sitemap.service.type.SiteMapChangeFreqType", gridOrder = 3, prominent = true)
    protected String changeFreq;

    @Column(name = "PRIORITY", nullable = false)
    @AdminPresentation(friendlyName = "SiteMapURLEntryImpl_Priority", fieldType = SupportedFieldType.WAKA_ENUMERATION,
            wakaEnumeration = "com.wakacommerce.common.sitemap.service.type.SiteMapPriorityType", gridOrder = 4, prominent = true)
    protected String priority;
    
    @ManyToOne(targetEntity = CustomUrlSiteMapGeneratorConfigurationImpl.class, optional = false)
    @JoinColumn(name = "GEN_CONFIG_ID")
    protected CustomUrlSiteMapGeneratorConfiguration customUrlSiteMapGeneratorConfiguration;
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getLocation() {
        return location;
    }

    @Override
    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public Date getLastMod() {
        return lastModified;
    }

    @Override
    public void setLastMod(Date lastModified) {
        this.lastModified = lastModified;
    }

    @Override
    public SiteMapChangeFreqType getSiteMapChangeFreq() {
        if (changeFreq != null) {
            return SiteMapChangeFreqType.getInstance(this.changeFreq);
        } else {
            return null;
        }
    }

    @Override
    public void setSiteMapChangeFreq(SiteMapChangeFreqType siteMapChangeFreq) {
        if (siteMapChangeFreq != null) {
            this.changeFreq = siteMapChangeFreq.getType();
        } else {
            this.changeFreq = null;
        }
    }

    @Override
    public SiteMapPriorityType getSiteMapPriority() {
        if (priority != null) {
            return SiteMapPriorityType.getInstance(this.priority);
        } else {
            return null;
        }
    }

    @Override
    public void setSiteMapPriority(SiteMapPriorityType siteMapPriority) {
        if (siteMapPriority != null) {
            this.priority = siteMapPriority.getType();
        } else {
            this.priority = null;
        }

    }

    @Override
    public CustomUrlSiteMapGeneratorConfiguration getCustomUrlSiteMapGeneratorConfiguration() {
        return customUrlSiteMapGeneratorConfiguration;
    }

    @Override
    public void setCustomUrlSiteMapGeneratorConfiguration(CustomUrlSiteMapGeneratorConfiguration customUrlSiteMapGeneratorConfiguration) {
        this.customUrlSiteMapGeneratorConfiguration = customUrlSiteMapGeneratorConfiguration;
    }

}

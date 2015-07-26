

package com.wakacommerce.common.sitemap.domain;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.sitemap.service.type.SiteMapChangeFreqType;
import com.wakacommerce.common.sitemap.service.type.SiteMapGeneratorType;
import com.wakacommerce.common.sitemap.service.type.SiteMapPriorityType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 *Joshua Skorton (jskorton)
 */
@Entity
@Table(name = "BLC_SITE_MAP_GEN_CFG")
@Inheritance(strategy = InheritanceType.JOINED)
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region = "blConfigurationModuleElements")
@AdminPresentationClass(friendlyName = "SiteMapGeneratorConfigurationImpl")
public class SiteMapGeneratorConfigurationImpl implements SiteMapGeneratorConfiguration {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "GeneratorConfigurationId")
    @GenericGenerator(
            name = "GeneratorConfigurationId",
            strategy = "com.wakacommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name = "segment_value", value = "SiteMapGeneratorConfigurationImpl"),
                    @Parameter(name = "entity_name", value = "com.wakacommerce.common.sitemap.domain.SiteMapGeneratorConfigurationImpl")
            })
    @Column(name = "GEN_CONFIG_ID")
    protected Long id;

    @Column(name = "DISABLED", nullable = false)
    @AdminPresentation(friendlyName = "SiteMapGeneratorConfigurationImpl_Disabled", gridOrder = 2, prominent = true)
    protected Boolean disabled = false;
    
    @Column(name = "CHANGE_FREQ", nullable = false)
    @AdminPresentation(friendlyName = "SiteMapGeneratorConfigurationImpl_Change_Freq", fieldType = SupportedFieldType.BROADLEAF_ENUMERATION,
            broadleafEnumeration = "com.wakacommerce.common.sitemap.service.type.SiteMapChangeFreqType", gridOrder = 3, prominent = true)
    protected String changeFreq;

    @Column(name = "PRIORITY", nullable = true)
    @AdminPresentation(friendlyName = "SiteMapGeneratorConfigurationImpl_Priority", fieldType = SupportedFieldType.BROADLEAF_ENUMERATION,
            broadleafEnumeration = "com.wakacommerce.common.sitemap.service.type.SiteMapPriorityType", gridOrder = 4, prominent = true)
    protected String priority;

    @Column(name = "GENERATOR_TYPE", nullable = false)
    @AdminPresentation(friendlyName = "SiteMapGeneratorConfigurationImpl_Generator_Type", fieldType = SupportedFieldType.BROADLEAF_ENUMERATION,
            broadleafEnumeration = "com.wakacommerce.common.sitemap.service.type.SiteMapGeneratorType", gridOrder = 1, prominent = true)
    protected String generatorType;
    
    @ManyToOne(targetEntity = SiteMapConfigurationImpl.class, optional = false)
    @JoinColumn(name = "MODULE_CONFIG_ID")
    protected SiteMapConfiguration siteMapConfiguration;
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Boolean isDisabled() {
        return disabled;
    }

    @Override
    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
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
    public SiteMapGeneratorType getSiteMapGeneratorType() {
        if (generatorType != null) {
            return SiteMapGeneratorType.getInstance(this.generatorType);
        } else {
            return null;
        }
    }

    @Override
    public void setSiteMapGeneratorType(SiteMapGeneratorType siteMapGeneratorType) {
        if (siteMapGeneratorType != null) {
            this.generatorType = siteMapGeneratorType.getType();
        } else {
            this.generatorType = null;
        }
    }

    @Override
    public SiteMapConfiguration getSiteMapConfiguration() {
        return siteMapConfiguration;
    }

    @Override
    public void setSiteMapConfiguration(SiteMapConfiguration siteMapConfiguration) {
        this.siteMapConfiguration = siteMapConfiguration;
    }
}

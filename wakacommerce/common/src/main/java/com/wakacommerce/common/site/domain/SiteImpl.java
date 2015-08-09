
package com.wakacommerce.common.site.domain;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.persistence.ArchiveStatus;
import com.wakacommerce.common.persistence.Status;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.RequiredOverride;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.site.service.type.SiteResolutionType;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @ hui
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SITE")
@Cache(usage= CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blStandardElements")
@AdminPresentationClass(friendlyName = "baseSite")
@SQLDelete(sql="UPDATE BLC_SITE SET ARCHIVED = 'Y' WHERE SITE_ID = ?")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITEMARKER)
})
public class SiteImpl implements Site, AdminMainEntity {

    private static final long serialVersionUID = 1L;
    private static final Log LOG = LogFactory.getLog(SiteImpl.class);

    @Id
    @GeneratedValue(generator = "SiteId")
    @GenericGenerator(
        name="SiteId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="SiteImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.common.site.domain.SiteImpl")
        }
    )
    @Column(name = "SITE_ID")
    protected Long id;

    @Column (name = "NAME")
    @AdminPresentation(friendlyName = "SiteImpl_Site_Name", order = 1000,
            gridOrder = 1, prominent = true, requiredOverride = RequiredOverride.REQUIRED)
    protected String name;

    @Column (name = "SITE_IDENTIFIER_TYPE")
    @AdminPresentation(friendlyName = "SiteImpl_Site_Identifier_Type", order = 2000,
            gridOrder = 2, prominent = true,
            wakaEnumeration = "com.wakacommerce.common.site.service.type.SiteResolutionType", requiredOverride=RequiredOverride.REQUIRED,
            fieldType = SupportedFieldType.WAKA_ENUMERATION)
    protected String siteIdentifierType;

    @Column (name = "SITE_IDENTIFIER_VALUE")
    @AdminPresentation(friendlyName = "SiteImpl_Site_Identifier_Value", order = 3000,
            gridOrder = 3, prominent = true, requiredOverride=RequiredOverride.REQUIRED)
    @Index(name = "BLC_SITE_ID_VAL_INDEX", columnNames = { "SITE_IDENTIFIER_VALUE" })
    protected String siteIdentifierValue;

    @Column(name = "DEACTIVATED")
    @AdminPresentation(friendlyName = "SiteImpl_Deactivated", order = 4000,
            gridOrder = 4, excluded = false,
            defaultValue = "false")
    protected Boolean deactivated = false;
    
    @ManyToMany(targetEntity = CatalogImpl.class, cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "BLC_SITE_CATALOG", joinColumns = @JoinColumn(name = "SITE_ID"), inverseJoinColumns = @JoinColumn(name = "CATALOG_ID"))
    @BatchSize(size = 50)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @AdminPresentation(excluded = true)
    protected List<Catalog> catalogs = new ArrayList<Catalog>();

    /**************************************************/

    /**************************************************/

    @Embedded
    protected ArchiveStatus archiveStatus = new ArchiveStatus();
    
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSiteIdentifierType() {
        return siteIdentifierType;
    }

    @Override
    public void setSiteIdentifierType(String siteIdentifierType) {
        this.siteIdentifierType = siteIdentifierType;
    }

    @Override
    public String getSiteIdentifierValue() {
        return siteIdentifierValue;
    }

    @Override
    public void setSiteIdentifierValue(String siteIdentifierValue) {
        this.siteIdentifierValue = siteIdentifierValue;
    }

    @Override
    public SiteResolutionType getSiteResolutionType() {
        return siteIdentifierType == null ? null : SiteResolutionType.getInstance(siteIdentifierType);
    }

    @Override
    public void setSiteResolutionType(SiteResolutionType siteResolutionType) {
        this.siteIdentifierType = siteResolutionType == null ? null : siteResolutionType.getType();
    }

    @Override
    public List<Catalog> getCatalogs() {
        return catalogs;
    }

    @Override
    public void setCatalogs(List<Catalog> catalogs) {
        this.catalogs = catalogs;
    }
    
    @Override
    public Character getArchived() {
       ArchiveStatus temp;
       if (archiveStatus == null) {
           temp = new ArchiveStatus();
       } else {
           temp = archiveStatus;
       }
       return temp.getArchived();
    }

    @Override
    public void setArchived(Character archived) {
       if (archiveStatus == null) {
           archiveStatus = new ArchiveStatus();
       }
       archiveStatus.setArchived(archived);
    }
    
    @Override
    public ArchiveStatus getArchiveStatus() {
        return archiveStatus;
    }

    @Override
    public boolean isActive() {
        if (LOG.isDebugEnabled()) {
            if (isDeactivated()) {
                LOG.debug("site, " + id + ", inactive due to deactivated property");
            }
            if ('Y'==getArchived()) {
                LOG.debug("site, " + id + ", inactive due to archived status");
            }
        }
        return !isDeactivated() && 'Y'!=getArchived();
    }

    @Override
    public boolean isDeactivated() {
        if (deactivated == null) {
            return false;
        } else {
            return deactivated;
        }
    }

    @Override
    public void setDeactivated(boolean deactivated) {
        this.deactivated = deactivated;
    }
    
    @Override
    public boolean isTemplateSite() {
        return false;
    }

    public void checkCloneable(Site site) throws CloneNotSupportedException, SecurityException, NoSuchMethodException {
        Method cloneMethod = site.getClass().getMethod("clone", new Class[]{});
        if (cloneMethod.getDeclaringClass().getName().startsWith("com.wakacommerce") && !site.getClass().getName().startsWith("com.wakacommerce")) {
            //subclass is not implementing the clone method
            throw new CloneNotSupportedException("Custom extensions and implementations should implement clone.");
        }
    }

    @Override
    public Site clone() {
        Site clone;
        try {
            clone = (Site) Class.forName(this.getClass().getName()).newInstance();
            try {
                checkCloneable(clone);
            } catch (CloneNotSupportedException e) {
                LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: " + clone.getClass().getName(), e);
            }
            clone.setId(id);
            clone.setName(name);
            clone.setDeactivated(isDeactivated());
            clone.setSiteResolutionType(getSiteResolutionType());
            clone.setSiteIdentifierValue(getSiteIdentifierValue());
            ((Status) clone).setArchived(getArchived());

            for (Catalog catalog : getCatalogs()) {
                Catalog cloneCatalog = new CatalogImpl();

                cloneCatalog.setId(catalog.getId());
                cloneCatalog.setName(catalog.getName());
                clone.getCatalogs().add(cloneCatalog);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return clone;
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }
}


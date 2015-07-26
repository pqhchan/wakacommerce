
package com.wakacommerce.core.catalog.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.i18n.service.DynamicTranslationProvider;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.client.VisibilityEnum;

import javax.persistence.CascadeType;
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
 * The Class SkuAttributeImpl is the default implementation of {@link SkuAttribute}.
 * A SKU Attribute is a designator on a SKU that differentiates it from other similar SKUs
 * (for example: Blue attribute for hat).
 * If you want to add fields specific to your implementation of BroadLeafCommerce you should extend
 * this class and add your fields.  If you need to make significant changes to the SkuImpl then you
 * should implement your own version of {@link Sku}.
 * <br>
 * <br>
 * This implementation uses a Hibernate implementation of JPA configured through annotations.
 * The Entity references the following tables:
 * BLC_SKU_ATTRIBUTES,
 * 
 * 
 *   @see {@link SkuAttribute}, {@link SkuImpl}
 *  btaylor
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name="BLC_SKU_ATTRIBUTE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blProducts")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.SANDBOX, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_CATALOG, skipOverlaps=true)
})
public class SkuAttributeImpl implements SkuAttribute {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The id. */
    @Id
    @GeneratedValue(generator= "SkuAttributeId")
    @GenericGenerator(
        name="SkuAttributeId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="SkuAttributeImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.catalog.domain.SkuAttributeImpl")
        }
    )
    @Column(name = "SKU_ATTR_ID")
    protected Long id;
    
    /** The name. */
    @Column(name = "NAME", nullable=false)
    @Index(name="SKUATTR_NAME_INDEX", columnNames={"NAME"})
    @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
    protected String name;

    /** The value. */
    @Column(name = "VALUE", nullable=false)
    @AdminPresentation(friendlyName = "SkuAttributeImpl_Attribute_Value", order=2, group = "SkuAttributeImpl_Description", prominent=true)
    protected String value;

    /** The searchable. */
    @Column(name = "SEARCHABLE")
    @AdminPresentation(excluded = true)
    protected Boolean searchable = false;
  
    /** The sku. */
    @ManyToOne(targetEntity = SkuImpl.class, optional=false, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "SKU_ID")
    @Index(name="SKUATTR_SKU_INDEX", columnNames={"SKU_ID"})
    protected Sku sku;

    /* (non-Javadoc)
     * @see com.wakacommerce.core.catalog.domain.SkuAttribute#getId()
     */
    @Override
    public Long getId() {
        return id;
    }

    /* (non-Javadoc)
     * @see com.wakacommerce.core.catalog.domain.SkuAttribute#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

    /* (non-Javadoc)
     * @see com.wakacommerce.core.catalog.domain.SkuAttribute#getValue()
     */
    @Override
    public String getValue() {
        return DynamicTranslationProvider.getValue(this, "value", value);
    }

    /* (non-Javadoc)
     * @see com.wakacommerce.core.catalog.domain.SkuAttribute#setValue(java.lang.String)
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }

    /* (non-Javadoc)
     * @see com.wakacommerce.core.catalog.domain.SkuAttribute#getSearchable()
     */
    @Override
    public Boolean getSearchable() {
        if (searchable == null) {
            return Boolean.FALSE;
        } else {
            return searchable;
        }
    }

    /* (non-Javadoc)
     * @see com.wakacommerce.core.catalog.domain.SkuAttribute#setSearchable(java.lang.Boolean)
     */
    @Override
    public void setSearchable(Boolean searchable) {
        this.searchable = searchable;
    }
    
    /* (non-Javadoc)
     * @see com.wakacommerce.core.catalog.domain.SkuAttribute#getName()
     */
    @Override
    public String getName() {
        return DynamicTranslationProvider.getValue(this, "name", name);
    }

    /* (non-Javadoc)
     * @see com.wakacommerce.core.catalog.domain.SkuAttribute#setName(java.lang.String)
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return value;
    }
    
    /* (non-Javadoc)
     * @see com.wakacommerce.core.catalog.domain.SkuAttribute#getSku()
     */
    @Override
    public Sku getSku() {
        return sku;
    }

    /* (non-Javadoc)
     * @see com.wakacommerce.core.catalog.domain.SkuAttribute#setSku(com.wakacommerce.core.catalog.domain.Sku)
     */
    @Override
    public void setSku(Sku sku) {
        this.sku = sku;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((sku == null) ? 0 : sku.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        SkuAttributeImpl other = (SkuAttributeImpl) obj;

        if (id != null && other.id != null) {
            return id.equals(other.id);
        }

        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (sku == null) {
            if (other.sku != null)
                return false;
        } else if (!sku.equals(other.sku))
            return false;
        if (value == null) {
            if (other.value != null)
                return false;
        } else if (!value.equals(other.value))
            return false;
        return true;
    }

    @Override
    public <G extends SkuAttribute> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        SkuAttribute cloned = createResponse.getClone();
        cloned.setName(name);
        if (sku != null) {
            cloned.setSku(sku.createOrRetrieveCopyInstance(context).getClone());
        }
        cloned.setSearchable(getSearchable());
        cloned.setValue(value);
        return createResponse;
    }
}
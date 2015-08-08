  
package com.wakacommerce.profile.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.i18n.service.DynamicTranslationProvider;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

/**
 *  
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_COUNTRY_SUB_CAT")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(friendlyName = "CountrySubdivisionCategoryImpl_baseCategory")
public class CountrySubdivisionCategoryImpl implements CountrySubdivisionCategory, AdminMainEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "CountrySubdivisionCategoryId")
    @GenericGenerator(
            name="CountrySubdivisionCategoryId",
            strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
            parameters = {
                    @Parameter(name="segment_value", value="CountrySubdivisionCategoryImpl"),
                    @Parameter(name="entity_name", value="com.wakacommerce.profile.core.domain.CountrySubdivisionCategoryImpl")
            }
    )
    @Column(name = "COUNTRY_SUB_CAT_ID")
    protected Long id;

    @Column(name = "NAME", nullable=false)
    @AdminPresentation(friendlyName = "CountrySubdivisionCategoryImpl_Name", order=1, prominent = true)
    protected String name;

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
        return DynamicTranslationProvider.getValue(this, "name", name);
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }
}

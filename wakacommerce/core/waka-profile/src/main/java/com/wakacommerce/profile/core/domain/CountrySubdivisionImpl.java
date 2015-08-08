  
package com.wakacommerce.profile.core.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.i18n.service.DynamicTranslationProvider;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *  
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_COUNTRY_SUB")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
@AdminPresentationClass(friendlyName = "CountrySubdivisionImpl_baseSubdivision")
public class CountrySubdivisionImpl implements CountrySubdivision, AdminMainEntity {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ABBREVIATION")
    protected String abbreviation;

    @Column(name = "NAME", nullable = false)
    @Index(name="COUNTRY_SUB_NAME_IDX", columnNames={"NAME"})
    @AdminPresentation(friendlyName = "CountrySubdivisionImpl_Name", order=9, group = "CountrySubdivisionImpl_Address", prominent = true)
    protected String name;

    @Column(name = "ALT_ABBREVIATION")
    @Index(name="COUNTRY_SUB_ALT_ABRV_IDX", columnNames={"ALT_ABBREVIATION"})
    @AdminPresentation(friendlyName = "CountrySubdivisionImpl_AltAbbreviation", order=10, group = "CountrySubdivisionImpl_Address", prominent = true)
    protected String alternateAbbreviation;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = CountryImpl.class, optional = false)
    @JoinColumn(name = "COUNTRY")
    protected Country country;

    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = CountrySubdivisionCategoryImpl.class)
    @JoinColumn(name = "COUNTRY_SUB_CAT")
    protected CountrySubdivisionCategory category;

    @Override
    public String getAbbreviation() {
        return abbreviation;
    }

    @Override
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Override
    public String getAlternateAbbreviation() {
        return alternateAbbreviation;
    }

    @Override
    public void setAlternateAbbreviation(String alternateAbbreviation) {
        this.alternateAbbreviation = alternateAbbreviation;
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
    public Country getCountry() {
        return country;
    }

    @Override
    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public CountrySubdivisionCategory getCategory() {
        return category;
    }

    @Override
    public void setCategory(CountrySubdivisionCategory category) {
        this.category = category;
    }

    @Override
    public String getMainEntityName() {
        return getName();
    }
}

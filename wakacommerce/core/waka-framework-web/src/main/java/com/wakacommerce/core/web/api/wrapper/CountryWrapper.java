
package com.wakacommerce.core.web.api.wrapper;

import org.springframework.context.ApplicationContext;

import com.wakacommerce.profile.core.domain.Country;
import com.wakacommerce.profile.core.service.CountryService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a JAXB wrapper around Country.
 *
 * User: Elbert Bautista
 * Date: 4/10/12
 */
@XmlRootElement(name = "country")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CountryWrapper extends BaseWrapper implements APIWrapper<Country>, APIUnwrapper<Country> {

    @XmlElement
    protected String name;

    @XmlElement
    protected String abbreviation;

    @Override
    public void wrapDetails(Country model, HttpServletRequest request) {
        this.name = model.getName();
        this.abbreviation = model.getAbbreviation();
    }

    @Override
    public void wrapSummary(Country model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    @Override
    public Country unwrap(HttpServletRequest request, ApplicationContext appContext) {
        CountryService countryService = (CountryService) appContext.getBean("blCountryService");
        if (this.abbreviation != null) {
            Country country = countryService.findCountryByAbbreviation(this.abbreviation);
            return country;
        }

        return null;
    }

    
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    
    /**
     * @return the abbreviation
     */
    public String getAbbreviation() {
        return abbreviation;
    }

    
    /**
     * @param abbreviation the abbreviation to set
     */
    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}

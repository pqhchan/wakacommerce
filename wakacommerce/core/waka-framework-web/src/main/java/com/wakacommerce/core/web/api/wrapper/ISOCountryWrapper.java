  
package com.wakacommerce.core.web.api.wrapper;

import org.springframework.context.ApplicationContext;

import com.wakacommerce.common.i18n.domain.ISOCountry;
import com.wakacommerce.common.i18n.service.ISOService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a JAXB wrapper around ISOCountry.
 *
 *  
 */
@XmlRootElement(name = "isoCountry")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ISOCountryWrapper extends BaseWrapper implements APIWrapper<ISOCountry>, APIUnwrapper<ISOCountry> {

    @XmlElement
    protected String name;

    @XmlElement
    protected String alpha2;

    @Override
    public void wrapDetails(ISOCountry model, HttpServletRequest request) {
        this.name = model.getName();
        this.alpha2 = model.getAlpha2();
    }

    @Override
    public void wrapSummary(ISOCountry model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    @Override
    public ISOCountry unwrap(HttpServletRequest request, ApplicationContext appContext) {
        ISOService isoService = (ISOService) appContext.getBean("blISOService");
        if (this.alpha2 != null) {
            return isoService.findISOCountryByAlpha2Code(this.alpha2);
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
     * @return the alpha2
     */
    public String getAlpha2() {
        return alpha2;
    }

    
    /**
     * @param alpha2 the alpha2 to set
     */
    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }
}

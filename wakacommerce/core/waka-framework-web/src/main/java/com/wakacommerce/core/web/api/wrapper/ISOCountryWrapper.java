
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
 *
 * @ hui
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlpha2() {
        return alpha2;
    }

    public void setAlpha2(String alpha2) {
        this.alpha2 = alpha2;
    }
}

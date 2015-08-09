
package com.wakacommerce.core.web.api.wrapper;

import org.springframework.context.ApplicationContext;

import com.wakacommerce.profile.core.domain.State;
import com.wakacommerce.profile.core.service.StateService;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @ hui
 */
@XmlRootElement(name = "state")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class StateWrapper extends BaseWrapper implements APIWrapper<State>, APIUnwrapper<State> {

    @XmlElement
    protected String name;

    @XmlElement
    protected String abbreviation;

    @Override
    public void wrapDetails(State model, HttpServletRequest request) {
        this.name = model.getName();
        this.abbreviation = model.getAbbreviation();
    }

    @Override
    public void wrapSummary(State model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    @Override
    public State unwrap(HttpServletRequest request, ApplicationContext appContext) {
        StateService stateService = (StateService) appContext.getBean("blStateService");
        if (this.abbreviation != null) {
            State state = stateService.findStateByAbbreviation(this.abbreviation);
            return state;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }
}



package com.wakacommerce.core.web.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.wakacommerce.profile.core.domain.AdditionalFields;

/**
 *
 * @ hui
 */
public abstract class BaseWrapper implements ApplicationContextAware {

    @XmlTransient
    protected ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    protected List<MapElementWrapper> createElementWrappers(AdditionalFields model) {

        if (model.getAdditionalFields() != null && !model.getAdditionalFields().isEmpty()) {
            List<MapElementWrapper> mapElementWrappers = new ArrayList<MapElementWrapper>();
            for (String key : model.getAdditionalFields().keySet()) {
                MapElementWrapper mapElementWrapper = new MapElementWrapper();
                mapElementWrapper.setKey(key);
                mapElementWrapper.setValue(model.getAdditionalFields().get(key));
                mapElementWrappers.add(mapElementWrapper);
            }
            return mapElementWrappers;
        }
        return null;

    }
}

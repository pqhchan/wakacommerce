
package com.wakacommerce.common.jmx;

import org.springframework.beans.factory.FactoryBean;

/**
 * 
 *jfischer
 *
 */
public class ExplicitNameFactoryBean implements FactoryBean {
    
    private final String name;
    private final String suffix;
    
    public ExplicitNameFactoryBean(String name, String suffix) {
        this.name = name;
        this.suffix = suffix;
    }

    public Object getObject() throws Exception {
        return name + "-" + suffix;
    }

    @SuppressWarnings("unchecked")
    public Class getObjectType() {
        return String.class;
    }

    public boolean isSingleton() {
        return false;
    }

}

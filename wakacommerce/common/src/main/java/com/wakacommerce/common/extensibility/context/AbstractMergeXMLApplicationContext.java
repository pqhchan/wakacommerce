package com.wakacommerce.common.extensibility.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractXmlApplicationContext;
import org.springframework.core.io.Resource;

public abstract class AbstractMergeXMLApplicationContext extends AbstractXmlApplicationContext {

    protected Resource[] configResources;
    
    protected Resource[] getConfigResources() {
        return this.configResources;
    }
    
    public AbstractMergeXMLApplicationContext(ApplicationContext parent) {
        super(parent);
    }

}

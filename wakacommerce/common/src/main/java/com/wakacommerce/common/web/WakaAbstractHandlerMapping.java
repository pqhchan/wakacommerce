package com.wakacommerce.common.web;

import org.springframework.web.servlet.handler.AbstractHandlerMapping;

public abstract class WakaAbstractHandlerMapping extends AbstractHandlerMapping {

	protected String controllerName;

    /**
     * 不提供默认处理器
     */
    @Override
    public Object getDefaultHandler() {
        return null;        
    }
    
    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }
    
}

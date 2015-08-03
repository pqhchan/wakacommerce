package com.wakacommerce.openadmin.server.service.artifact.image.effects.chain.conversion;

public class Parameter {

    private Class<?> parameterClass;
    private Object parameterInstance;
    
    public Class<?> getParameterClass() {
        return parameterClass;
    }
    
    public void setParameterClass(Class<?> parameterClass) {
        this.parameterClass = parameterClass;
    }
    
    public Object getParameterInstance() {
        return parameterInstance;
    }
    
    public void setParameterInstance(Object parameterInstance) {
        this.parameterInstance = parameterInstance;
    }
    
}

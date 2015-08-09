package com.wakacommerce.common.web.expression;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.wakacommerce.common.config.domain.SystemProperty;
import com.wakacommerce.common.util.BLCSystemProperty;
import com.wakacommerce.common.web.processor.ConfigVariableProcessor;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public class PropertiesVariableExpression implements BroadleafVariableExpression {
    
    @Override
    public String getName() {
        return "props";
    }
    
    public String get(String propertyName) {
        return BLCSystemProperty.resolveSystemProperty(propertyName);
    }

    public int getAsInt(String propertyName) {
        return BLCSystemProperty.resolveIntSystemProperty(propertyName);
    }
    
    public boolean getAsBoolean(String propertyName) {
        return BLCSystemProperty.resolveBooleanSystemProperty(propertyName); 
    }
    
    public long getAsLong(String propertyName) {
        return BLCSystemProperty.resolveLongSystemProperty(propertyName); 
    }

    public boolean getForceShowIdColumns() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        boolean forceShow = BLCSystemProperty.resolveBooleanSystemProperty("listGrid.forceShowIdColumns");
        forceShow = forceShow || "true".equals(request.getParameter("showIds"));
        
        return forceShow;
    }
    
}

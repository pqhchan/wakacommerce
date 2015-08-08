  
package com.wakacommerce.core.web.api.jaxrs;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * <p>
 * Condition class that checks for the presence of a JAXRS class to determine if JAXRS is actually loaded
 *
 * <p>
 * By default, this checks the existence of javax.ws.rs.core.UriInfo
 *
 *     
 */
public class IsJaxrsAvailableCondition implements Condition {

    /**
     * Fully-qualified name of a class that is representative of JAXRS being loaded
     */
    public static String JAXRSCLASS = "javax.ws.rs.core.UriInfo";
    
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        try {
            ClassUtils.getClass(JAXRSCLASS);
        } catch (ClassNotFoundException e) {
            return false;
        }
        return true;
    }

}

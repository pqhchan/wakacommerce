
package com.wakacommerce.core.web.api.jaxrs;

import org.apache.commons.lang3.ClassUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 *
 * @ hui
 */
public class IsJaxrsAvailableCondition implements Condition {

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

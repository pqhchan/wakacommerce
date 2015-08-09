
package com.wakacommerce.common.extensibility.jpa.copy;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @ hui
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DirectCopyTransform {

    DirectCopyTransformMember[] value();

}

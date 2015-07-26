
package com.wakacommerce.common.extensibility.jpa.clone;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 *Jeff Fischer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ClonePolicyMap {

    boolean deepClone() default true;

    String toOneProperty() default "";

}

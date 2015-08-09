
package com.wakacommerce.common.extensibility.jpa.clone;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @ hui
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface ClonePolicyCollection {

    boolean deepClone() default true;

    String toOneProperty() default "";

    boolean unowned() default false;

    boolean useProductionFiltering() default false;
}

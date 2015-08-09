
package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wakacommerce.common.presentation.client.LookupType;

/**
 *
 * @ hui
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AdminPresentationToOneLookup {

    String lookupDisplayProperty() default "";

    String[] customCriteria() default {};

    boolean useServerSideInspectionCache() default true;

    LookupType lookupType() default LookupType.STANDARD;

    boolean forcePopulateChildProperties() default false;

    boolean enableTypeaheadLookup() default false;

}

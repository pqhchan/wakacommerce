
package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wakacommerce.common.enumeration.domain.DataDrivenEnumerationValueImpl;

/**
 *
 * @ hui
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AdminPresentationDataDrivenEnumeration {

    Class<?> optionListEntity() default DataDrivenEnumerationValueImpl.class;

    OptionFilterParam[] optionFilterParams() default {};

    String optionValueFieldName() default "";

    String optionDisplayFieldName() default "";

    boolean optionCanEditValues() default false;
}

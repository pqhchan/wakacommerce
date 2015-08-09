package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wakacommerce.common.presentation.client.CustomFieldSearchableTypes;

/**
 *
 * @ hui
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AdminPresentationMapField {

    String fieldName();

    AdminPresentation fieldPresentation();

    Class<?> targetClass() default Void.class;

    CustomFieldSearchableTypes searchable() default CustomFieldSearchableTypes.NOT_SPECIFIED;

    String manyToField() default "";

}

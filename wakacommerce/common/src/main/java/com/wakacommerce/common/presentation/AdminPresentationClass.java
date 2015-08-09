package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface AdminPresentationClass {

    PopulateToOneFieldsEnum populateToOneFields() default PopulateToOneFieldsEnum.NOT_SPECIFIED;

    String friendlyName() default "";

    String ceilingDisplayEntity() default "";

    boolean excludeFromPolymorphism() default false;

}

package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.presentation.client.VisibilityEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AdminPresentation {

    String friendlyName() default "";

    int order() default 99999;

    int gridOrder() default 9999;

    VisibilityEnum visibility() default VisibilityEnum.VISIBLE_ALL;

    SupportedFieldType fieldType() default SupportedFieldType.UNKNOWN;

    String group() default "General";

    int groupOrder() default 99999;

    String tab() default "General";

    int tabOrder() default 100;

    boolean largeEntry() default false;

    boolean prominent() default false;

    String columnWidth() default "*";

    String wakaEnumeration() default "";

    boolean readOnly() default false;

    ValidationConfiguration[] validationConfigurations() default {};

    RequiredOverride requiredOverride() default RequiredOverride.IGNORED;

    boolean excluded() default false;

    String tooltip() default "";

    String helpText() default "";

    String hint() default "";

    String showIfProperty() default "";

    String ruleIdentifier() default "";

    String defaultValue() default "";
}

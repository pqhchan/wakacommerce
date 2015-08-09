package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wakacommerce.common.presentation.client.OperationType;

/**
 *
 * @ hui
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AdminPresentationAdornedTargetCollection {

    String friendlyName() default "";

    String securityLevel() default "";

    boolean excluded() default false;

    String showIfProperty() default "";

    boolean readOnly() default false;

    boolean useServerSideInspectionCache() default true;

    String parentObjectProperty() default "";

    String parentObjectIdProperty() default "id";

    String targetObjectProperty() default "";

    String[] maintainedAdornedTargetFields() default {};

    String[] gridVisibleFields() default {};

    String targetObjectIdProperty() default "id";

    String joinEntityClass() default "";

    String sortProperty() default "";

    boolean sortAscending() default true;

    boolean ignoreAdornedProperties() default false;

    int order() default 99999;

    String tab() default "General";

    int tabOrder() default 100;

    String[] customCriteria() default {};

    AdminPresentationOperationTypes operationTypes() default @AdminPresentationOperationTypes(addType = OperationType.ADORNEDTARGETLIST, fetchType = OperationType.ADORNEDTARGETLIST, inspectType = OperationType.BASIC, removeType = OperationType.ADORNEDTARGETLIST, updateType = OperationType.ADORNEDTARGETLIST);

    String currencyCodeField() default "";
}

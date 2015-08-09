
package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wakacommerce.common.presentation.client.AddMethodType;
import com.wakacommerce.common.presentation.client.OperationType;

/**
 *
 * @ hui
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AdminPresentationCollection {

    String friendlyName() default "";

    String securityLevel() default "";

    boolean excluded() default false;

    boolean readOnly() default false;

    boolean useServerSideInspectionCache() default true;

    AddMethodType addType() default AddMethodType.PERSIST;

    String manyToField() default "";

    int order() default 99999;

    String tab() default "General";

    int tabOrder() default 100;

    String[] customCriteria() default {};

    AdminPresentationOperationTypes operationTypes() default @AdminPresentationOperationTypes(addType = OperationType.BASIC, fetchType = OperationType.BASIC, inspectType = OperationType.BASIC, removeType = OperationType.BASIC, updateType = OperationType.BASIC);

    String showIfProperty() default "";

    String currencyCodeField() default "";

    String sortProperty() default "";

    boolean sortAscending() default true;
}

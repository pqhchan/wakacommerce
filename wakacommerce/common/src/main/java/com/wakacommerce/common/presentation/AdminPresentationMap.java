package com.wakacommerce.common.presentation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.persistence.MapKey;

import com.wakacommerce.common.presentation.client.OperationType;
import com.wakacommerce.common.presentation.client.UnspecifiedBooleanType;

/**
 *
 * @ hui
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AdminPresentationMap {

    String friendlyName() default "";

    String securityLevel() default "";

    boolean excluded() default false;

    boolean readOnly() default false;

    boolean useServerSideInspectionCache() default true;

    int order() default 99999;

    String tab() default "General";

    int tabOrder() default 100;

    Class<?> keyClass() default void.class;

    String mapKeyValueProperty() default "";

    String keyPropertyFriendlyName() default "Key";

    Class<?> valueClass() default void.class;

    boolean deleteEntityUponRemove() default false;

    String valuePropertyFriendlyName() default "Value";

    UnspecifiedBooleanType isSimpleValue() default UnspecifiedBooleanType.UNSPECIFIED;

    String toOneTargetProperty() default "";

    String toOneParentProperty() default "";

    String mediaField() default "";

    AdminPresentationMapKey[] keys() default {};

    boolean forceFreeFormKeys() default false;

    String manyToField() default "";

    Class<?> mapKeyOptionEntityClass() default void.class;

    String mapKeyOptionEntityDisplayField() default "";

    String mapKeyOptionEntityValueField() default "";

    String[] customCriteria() default {};

    AdminPresentationOperationTypes operationTypes() default @AdminPresentationOperationTypes(addType = OperationType.MAP, fetchType = OperationType.MAP, inspectType = OperationType.MAP, removeType = OperationType.MAP, updateType = OperationType.MAP);

    String showIfProperty() default "";

    String currencyCodeField() default "";

}

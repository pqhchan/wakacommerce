package com.wakacommerce.common.presentation.override;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.wakacommerce.common.presentation.AdminPresentationMapKey;
import com.wakacommerce.common.presentation.AdminPresentationOperationTypes;
import com.wakacommerce.common.presentation.OptionFilterParam;
import com.wakacommerce.common.presentation.ValidationConfiguration;
import com.wakacommerce.common.presentation.client.OperationType;

/**
 *
 * @ hui
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdminPresentationMergeEntry {

    String propertyType();

    String overrideValue() default "";

    double doubleOverrideValue() default Double.MIN_VALUE;

    float floatOverrideValue() default Float.MIN_VALUE;

    boolean booleanOverrideValue() default false;

    int intOverrideValue() default Integer.MIN_VALUE;

    long longOverrideValue() default Long.MIN_VALUE;

    String[] stringArrayOverrideValue() default {};

    double[] doubleArrayOverrideValue() default {};

    float[] floatArrayOverrideValue() default {};

    boolean[] booleanArrayOverrideValue() default {};

    int[] intArrayOverrideValue() default {};

    long[] longArrayOverrideValue() default {};

    ValidationConfiguration[] validationConfigurations() default {};

    AdminPresentationOperationTypes operationTypes() default @AdminPresentationOperationTypes(addType = OperationType.BASIC,
            fetchType = OperationType.BASIC, inspectType = OperationType.BASIC, removeType = OperationType.BASIC,
            updateType = OperationType.BASIC);

    OptionFilterParam[] optionFilterParams() default {};

    AdminPresentationMapKey[] keys() default {};
}

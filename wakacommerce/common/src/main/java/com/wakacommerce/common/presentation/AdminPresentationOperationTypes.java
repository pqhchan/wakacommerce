
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
public @interface AdminPresentationOperationTypes {

    OperationType addType() default OperationType.BASIC;

    OperationType updateType() default OperationType.BASIC;

    OperationType removeType() default OperationType.BASIC;

    OperationType fetchType() default OperationType.BASIC;

    OperationType inspectType() default OperationType.BASIC;

}

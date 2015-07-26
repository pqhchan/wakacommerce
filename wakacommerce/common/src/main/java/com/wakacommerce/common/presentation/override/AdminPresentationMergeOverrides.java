
package com.wakacommerce.common.presentation.override;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Allows a non-comprehensive override of admin presentation annotation
 * property values for a target entity field.
 *
 * @see com.wakacommerce.common.presentation.AdminPresentation
 * @see com.wakacommerce.common.presentation.AdminPresentationToOneLookup
 * @see com.wakacommerce.common.presentation.AdminPresentationDataDrivenEnumeration
 * @see com.wakacommerce.common.presentation.AdminPresentationCollection
 * @see com.wakacommerce.common.presentation.AdminPresentationAdornedTargetCollection
 * @see com.wakacommerce.common.presentation.AdminPresentationMap
 *Jeff Fischer
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdminPresentationMergeOverrides {

    /**
     * The new configurations for each field targeted for override
     *
     * @return field specific overrides
     */
    AdminPresentationMergeOverride[] value();

}

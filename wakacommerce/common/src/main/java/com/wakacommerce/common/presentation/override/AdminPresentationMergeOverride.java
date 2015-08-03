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
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AdminPresentationMergeOverride {

    /**
     * The name of the property whose admin presentation annotation should be overwritten
     *
     * @return the name of the property that should be overwritten
     */
    String name();

    /**
     * The array of override configuration values. Each entry correlates to a property on
     * {@link com.wakacommerce.common.presentation.AdminPresentation},
     * {@link com.wakacommerce.common.presentation.AdminPresentationToOneLookup},
     * {@link com.wakacommerce.common.presentation.AdminPresentationDataDrivenEnumeration},
     * {@link com.wakacommerce.common.presentation.AdminPresentationAdornedTargetCollection},
     * {@link com.wakacommerce.common.presentation.AdminPresentationCollection} or
     * {@link com.wakacommerce.common.presentation.AdminPresentationMap}
     *
     * @return The array of override configuration values.
     */
    AdminPresentationMergeEntry[] mergeEntries();
}

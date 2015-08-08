package com.wakacommerce.common.presentation.override;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
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
     * 要覆盖的属性
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
     */
    AdminPresentationMergeEntry[] mergeEntries();
}

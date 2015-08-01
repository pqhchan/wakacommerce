
package com.wakacommerce.common.presentation.override;

import com.wakacommerce.common.presentation.AdminPresentationMap;

/**
 * 
 * @deprecated use {@link AdminPresentationMergeOverrides} instead
 */
@Deprecated
public @interface AdminPresentationMapOverride {

    /**
     * The name of the property whose AdminPresentation annotation should be overwritten
     *
     * @return the name of the property that should be overwritten
     */
    String name();

    /**
     * The AdminPresentation to overwrite the property with
     *
     * @return the AdminPresentation being mapped to the attribute
     */
    AdminPresentationMap value();

}

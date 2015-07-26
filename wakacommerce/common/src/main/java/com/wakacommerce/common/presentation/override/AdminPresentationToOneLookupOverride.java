
package com.wakacommerce.common.presentation.override;

import com.wakacommerce.common.presentation.AdminPresentationToOneLookup;

/**
 *Jeff Fischer
 * @deprecated use {@link AdminPresentationMergeOverrides} instead
 */
@Deprecated
public @interface AdminPresentationToOneLookupOverride {

    /**
     * The name of the property whose AdminPresentationToOneLookup annotation should be overwritten
     *
     * @return the name of the property that should be overwritten
     */
    String name();

    /**
     * The AdminPresentationToOneLookup to overwrite the property with
     *
     * @return the AdminPresentation being mapped to the attribute
     */
    AdminPresentationToOneLookup value();

}

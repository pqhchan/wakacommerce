
package com.wakacommerce.common.extension;

/**
 * Used in conjunction with {@link com.wakacommerce.common.extension.SparselyPopulatedQueryExtensionHandler}. Describes
 * the current type of results desired from a query. STANDARD results relate specifically to a standard site (multitenant
 * concept). TEMPLATE results relate specifically to a template site's catalog or profile (also a multitenant concept)
 *
 *Jeff Fischer
 */
public enum ResultType {
    STANDARD,STANDARD_CACHE,TEMPLATE,TEMPLATE_CACHE,IGNORE
}

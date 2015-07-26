
package com.wakacommerce.cms.page.domain;

import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.value.ValueAssignable;

/**
 * Stores additional attributes for {@link Page}s
 * 
 *Andre Azzolini (apazzolini)
 */
public interface PageAttribute extends ValueAssignable<String>,MultiTenantCloneable<PageAttribute> {

    /**
     * Returns the id
     * @return the id
     */
    public Long getId();

    /**
     * Sets the id
     * @param id
     */
    public void setId(Long id);

    /**
     * Returns the {@link Page}
     * @return the Page
     */
    public Page getPage();

    /**
     * Sets the {@link Page}
     * @param page
     */
    public void setPage(Page page);

}

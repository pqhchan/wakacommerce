
package com.wakacommerce.core.search.domain;

import com.wakacommerce.core.search.redirect.domain.SearchRedirect;

/**
 * @deprecated Replaced in functionality by {@link SearchRedirect}
 */
@Deprecated
public interface SearchIntercept {

    public abstract String getTerm();

    public abstract void setTerm(String term);

    public abstract String getRedirect();

    public abstract void setRedirect(String redirect);

}

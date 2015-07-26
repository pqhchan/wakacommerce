
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.site.domain.Theme;

import javax.servlet.http.HttpServletRequest;

/**
 * Responsible for returning the theme used by Broadleaf Commerce for the current request.
 * For a single site installation, this should return a theme whose path and name are empty string.
 *
 *bpolster
 */
public interface BroadleafThemeResolver {
    
    /**
     * 
     * @deprecated Use {@link #resolveTheme(WebRequest)} instead
     */
    @Deprecated
    public Theme resolveTheme(HttpServletRequest request, Site site);
    
    public Theme resolveTheme(WebRequest request);
}

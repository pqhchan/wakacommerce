
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.common.site.domain.Theme;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public interface BroadleafThemeResolver {

    @Deprecated
    public Theme resolveTheme(HttpServletRequest request, Site site);
    
    public Theme resolveTheme(WebRequest request);
}

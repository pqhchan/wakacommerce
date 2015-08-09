
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.site.domain.Site;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
public interface BroadleafSandBoxResolver  {

    public static String SANDBOX_ID_VAR = "blSandboxId";

    @Deprecated
    public SandBox resolveSandBox(HttpServletRequest request, Site site);

    public SandBox resolveSandBox(WebRequest request, Site site);

}

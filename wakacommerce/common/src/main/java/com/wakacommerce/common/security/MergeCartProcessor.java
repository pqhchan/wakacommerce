
package com.wakacommerce.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ hui
 */
@Deprecated
public interface MergeCartProcessor {

    public void execute(HttpServletRequest request, HttpServletResponse response, Authentication authResult);

    public void execute(WebRequest request, Authentication authResult);

}

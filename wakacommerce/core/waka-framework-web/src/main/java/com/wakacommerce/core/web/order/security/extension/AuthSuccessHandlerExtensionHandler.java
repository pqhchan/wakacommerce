
package com.wakacommerce.core.web.order.security.extension;

import org.springframework.security.core.Authentication;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @ hui
 */
public interface AuthSuccessHandlerExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType preMergeCartExecution(HttpServletRequest request, HttpServletResponse response, 
            Authentication authentication);

}


package com.wakacommerce.core.web.order.security.extension;

import org.springframework.security.core.Authentication;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *
 * @ hui
 */
public class AbstractAuthSuccessHandlerExtensionHandler extends AbstractExtensionHandler implements 
    AuthSuccessHandlerExtensionHandler {

    @Override
    public ExtensionResultStatusType preMergeCartExecution(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        return ExtensionResultStatusType.NOT_HANDLED;
    }

}

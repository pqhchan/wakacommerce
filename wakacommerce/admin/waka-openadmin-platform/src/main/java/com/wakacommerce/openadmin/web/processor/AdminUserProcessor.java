
package com.wakacommerce.openadmin.web.processor;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;

import com.wakacommerce.common.web.dialect.AbstractModelVariableModifierProcessor;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;
import com.wakacommerce.openadmin.server.security.service.AdminSecurityService;

import javax.annotation.Resource;

/**
 * A Thymeleaf processor that will add the appropriate AdminUser to the model.
 *
 *  
 */
@Component("blAdminUserProcessor")
public class AdminUserProcessor extends AbstractModelVariableModifierProcessor {

    private static final String ANONYMOUS_USER_NAME = "anonymousUser";
    
    @Resource(name = "blAdminSecurityService")
    protected AdminSecurityService securityService;

    public AdminUserProcessor() {
        super("admin_user");
    }

    @Override
    protected void modifyModelAttributes(Arguments arguments, Element element) {
        String resultVar = element.getAttributeValue("resultVar");

        AdminUser user = getPersistentAdminUser();
        if (user != null) {
            addToModel(arguments, resultVar, user);
        }
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    protected AdminUser getPersistentAdminUser() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        if (ctx != null) {
            Authentication auth = ctx.getAuthentication();
            if (auth != null && !auth.getName().equals(ANONYMOUS_USER_NAME)) {
                UserDetails temp = (UserDetails) auth.getPrincipal();

                return securityService.readAdminUserByUserName(temp.getUsername());
            }
        }

        return null;
    }
}

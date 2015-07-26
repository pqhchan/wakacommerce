
package com.wakacommerce.openadmin.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.sandbox.service.SandBoxService;
import com.wakacommerce.common.web.BroadleafRequestContext;
import com.wakacommerce.common.web.SandBoxContext;
import com.wakacommerce.openadmin.server.security.domain.AdminUser;
import com.wakacommerce.openadmin.server.security.remote.SecurityVerifier;
import com.wakacommerce.openadmin.server.service.SandBoxMode;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *Jeff Fischer
 */
@Component("blAdminSandBoxFilter")
public class AdminSandBoxFilter extends OncePerRequestFilter {

    private static final String SANDBOX_ADMIN_ID_VAR = "blAdminCurrentSandboxId";
    private static String SANDBOX_ID_VAR = "blSandboxId";

    @Resource(name="blSandBoxService")
    protected SandBoxService sandBoxService;

    @Resource(name="blAdminSecurityRemoteService")
    protected SecurityVerifier adminRemoteSecurityService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // this filter is not currently wired in
        HttpSession session = request.getSession();
        AdminUser adminUser = adminRemoteSecurityService.getPersistentAdminUser();
        if (adminUser == null) {
            //clear any sandbox
            session.removeAttribute(SANDBOX_ADMIN_ID_VAR);
            SandBoxContext.setSandBoxContext(null);
        } else {
            BroadleafRequestContext brc = BroadleafRequestContext.getBroadleafRequestContext();
            if (brc != null) {
                brc.getAdditionalProperties().put("adminUser", adminUser);
            }

            Long overrideSandBoxId = adminUser.getOverrideSandBox() == null ? null : adminUser.getOverrideSandBox().getId();
            SandBox sandBox = sandBoxService.retrieveUserSandBox(adminUser.getId(), overrideSandBoxId, adminUser.getLogin());
            session.setAttribute(SANDBOX_ADMIN_ID_VAR, sandBox.getId());
            session.removeAttribute(SANDBOX_ID_VAR);
            AdminSandBoxContext context = new AdminSandBoxContext();
            context.setSandBoxId(sandBox.getId());
            context.setSandBoxMode(SandBoxMode.IMMEDIATE_COMMIT);
            context.setAdminUser(adminUser);
            SandBoxContext.setSandBoxContext(context);
        }
        try {
            filterChain.doFilter(request, response);
        } finally {
            SandBoxContext.setSandBoxContext(null);
        }
    }
}

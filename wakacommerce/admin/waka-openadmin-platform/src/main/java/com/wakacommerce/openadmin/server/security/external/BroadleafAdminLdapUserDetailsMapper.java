  
package com.wakacommerce.openadmin.server.security.external;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;

import com.wakacommerce.common.security.BroadleafExternalAuthenticationUserDetails;
import com.wakacommerce.common.site.domain.Site;
import com.wakacommerce.openadmin.server.security.service.AdminSecurityService;
import com.wakacommerce.openadmin.server.security.service.AdminUserProvisioningService;

import java.util.Collection;
import java.util.Map;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class BroadleafAdminLdapUserDetailsMapper extends LdapUserDetailsMapper {

    @Resource(name = "blAdminSecurityService")
    protected AdminSecurityService securityService;

    @Resource(name = "blAdminUserProvisioningService")
    protected AdminUserProvisioningService provisioningService;

    protected Map<String, String[]> roleNameSubstitutions;
    
    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        String email = (String) ctx.getObjectAttribute("mail");
        String firstName = (String) ctx.getObjectAttribute("givenName");
        String lastName = (String) ctx.getObjectAttribute("sn");
        
        BroadleafExternalAuthenticationUserDetails details = new BroadleafExternalAuthenticationUserDetails(username, "", authorities);
        details.setEmail(email);
        details.setFirstName(firstName);
        details.setLastName(lastName);
        details.setSite(determineSite(ctx, username, authorities));

        return provisioningService.provisionAdminUser(details);
    }

    protected Site determineSite(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        return null;
    }
}

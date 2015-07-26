
package com.wakacommerce.common.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.wakacommerce.common.site.domain.Site;

import java.util.Collection;

/**
 * This is an extension of Spring's User class to provide additional data to the UserDetails interface.  This should be used by derivitave
 * authentication providers to return an instance of UserDetails when authenticating against a system other than the Broadleaf tables (e.g. LDAP)
 * <p/>
 * User: Kelly Tisdell
 * Date: 6/19/12
 */
public class BroadleafExternalAuthenticationUserDetails extends User {
    
    private static final long serialVersionUID = 1L;

    private String firstName;
    
    private String lastName;
    
    private String email;
    
    private Site site;

    /**
     * This sets the username, password, and authorities.  
     * It also set the enabled, accountNonExpired, credentialsNonExpired, and accountNonLocked properties to true.
     * @param username
     * @param password
     * @param authorities
     */
    public BroadleafExternalAuthenticationUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
    }

    public BroadleafExternalAuthenticationUserDetails(String username, String password,
            boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
            boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

}

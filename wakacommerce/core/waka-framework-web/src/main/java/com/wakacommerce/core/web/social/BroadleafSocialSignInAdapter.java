
package com.wakacommerce.core.web.social;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Resource;


/**
 * The SignInAdapter is exclusively used for provider sign in so a SignInAdapter
 * bean will need to be added to the Spring Social configuration.
 *
 * The signIn() method takes the local application user's user ID normalized as a String.
 * No other credentials are necessary here because by the time this method is called the user will have signed
 * into the provider and their connection with that provider has been used to prove the user's identity.
 * This adapter will then authenticate manually against Spring Security
 *
 * To use:
 * this will automatically be injected into ProviderSignInController,
 * as long as this package is scanned
 * (make sure the following is in applicationContext-servlet.xml)
 * <context:component-scan base-package="com.wakacommerce.core.web"/>
 *
 * @see org.springframework.social.connect.web.ProviderSignInController
 *  
 *
 */
@Component("blSocialSignInAdapter")
public class BroadleafSocialSignInAdapter implements SignInAdapter {

    @Resource(name="blUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    public String signIn(String username, Connection<?> connection, NativeWebRequest request) {
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        return null;
    }

}

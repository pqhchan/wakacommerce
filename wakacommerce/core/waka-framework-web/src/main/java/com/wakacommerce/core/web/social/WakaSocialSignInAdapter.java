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
 *
 * @ hui
 */
@Component("blSocialSignInAdapter")
public class WakaSocialSignInAdapter implements SignInAdapter {

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

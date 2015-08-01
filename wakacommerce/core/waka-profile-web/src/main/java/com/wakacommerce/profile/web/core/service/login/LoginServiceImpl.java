package com.wakacommerce.profile.web.core.service.login;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.common.web.WakaWebRequestProcessor;
import com.wakacommerce.profile.core.domain.Customer;

import javax.annotation.Resource;


@Service("blLoginService")
public class LoginServiceImpl implements LoginService {

    @Resource(name="blAuthenticationManager")
    private AuthenticationManager authenticationManager;
    
    @Resource(name="blUserDetailsService")
    private UserDetailsService userDetailsService;

    @Resource(name = "blCartStateRequestProcessor")
    protected WakaWebRequestProcessor cartStateRequestProcessor;

    @Resource(name = "blCustomerStateRequestProcessor")
    private WakaWebRequestProcessor customerStateRequestProcessor;

    @Override
    public Authentication loginCustomer(Customer customer) {
        return loginCustomer(customer.getUsername(), customer.getUnencodedPassword());
    }
    
    @Override
    public Authentication loginCustomer(String username, String clearTextPassword) {
        UserDetails principal = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, clearTextPassword, principal.getAuthorities());
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        customerStateRequestProcessor.process(getWebRequest());
        cartStateRequestProcessor.process(getWebRequest());
        return authentication;
    }

    @Override
    public void logoutCustomer() {
        SecurityContextHolder.getContext().setAuthentication(null);
        customerStateRequestProcessor.process(getWebRequest());
        cartStateRequestProcessor.process(getWebRequest());
    }

    protected WebRequest getWebRequest() {
        return WakaRequestContext.getWakaRequestContext().getWebRequest();
    }

}

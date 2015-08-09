
package com.wakacommerce.profile.core.service;

import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.domain.CustomerRole;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Service("blUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource(name = "blCustomerService")
    protected CustomerService customerService;

    @Resource(name = "blRoleService")
    protected RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Customer customer = customerService.readCustomerByUsername(username, false);
        if (customer == null) {
            throw new UsernameNotFoundException("The customer was not found");
        }

        List<GrantedAuthority> grantedAuthorities = createGrantedAuthorities(roleService.findCustomerRolesByCustomerId(customer.getId()));
        return new CustomerUserDetails(customer.getId(), username, customer.getPassword(), !customer.isDeactivated(), true, !customer.isPasswordChangeRequired(), true, grantedAuthorities);
    }

    protected List<GrantedAuthority> createGrantedAuthorities(List<CustomerRole> customerRoles) {
        boolean roleUserFound = false;

        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        for (CustomerRole role : customerRoles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
            if (role.getRoleName().equals("ROLE_USER")) {
                roleUserFound = true;
            }
        }

        if (!roleUserFound) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return grantedAuthorities;
    }
    
}
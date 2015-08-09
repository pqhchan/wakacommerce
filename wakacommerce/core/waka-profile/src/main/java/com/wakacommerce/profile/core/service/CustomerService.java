
package com.wakacommerce.profile.core.service;

import org.springframework.security.authentication.dao.SaltSource;

import com.wakacommerce.common.security.util.PasswordChange;
import com.wakacommerce.common.security.util.PasswordReset;
import com.wakacommerce.common.service.GenericResponse;
import com.wakacommerce.profile.core.domain.Customer;
import com.wakacommerce.profile.core.service.handler.PasswordUpdatedHandler;
import com.wakacommerce.profile.core.service.listener.PostRegistrationObserver;

import java.util.List;

public interface CustomerService {

    public Customer saveCustomer(Customer customer);

    public Customer saveCustomer(Customer customer, boolean register);

    public Customer registerCustomer(Customer customer, String password, String passwordConfirm);

    public Customer readCustomerByUsername(String customerName);

    public Customer readCustomerByUsername(String username, Boolean cacheable);

    public Customer readCustomerByEmail(String emailAddress);

    public Customer changePassword(PasswordChange passwordChange);

    public Customer readCustomerById(Long userId);

    public Customer createCustomer();

    void deleteCustomer(Customer customer);

    public Customer createCustomerFromId(Long customerId);

    public Customer createNewCustomer();

    public void createRegisteredCustomerRoles(Customer customer);

    public void addPostRegisterListener(PostRegistrationObserver postRegisterListeners);

    public void removePostRegisterListener(PostRegistrationObserver postRegisterListeners);
    
    public Customer resetPassword(PasswordReset passwordReset);
    
    public List<PasswordUpdatedHandler> getPasswordResetHandlers();

    public void setPasswordResetHandlers(List<PasswordUpdatedHandler> passwordResetHandlers);
    
    public List<PasswordUpdatedHandler> getPasswordChangedHandlers();

    public void setPasswordChangedHandlers(List<PasswordUpdatedHandler> passwordChangedHandlers);

    GenericResponse sendForgotUsernameNotification(String emailAddress);

    GenericResponse sendForgotPasswordNotification(String userName, String forgotPasswordUrl);

    GenericResponse resetPasswordUsingToken(String username, String token, String password, String confirmPassword);

    @Deprecated
    public GenericResponse checkPasswordResetToken(String token);

    public GenericResponse checkPasswordResetToken(String token, Customer customer);

    public Long findNextCustomerId();

    @Deprecated
    public String getSalt();

    @Deprecated
    public void setSalt(String salt);

    @Deprecated
    public SaltSource getSaltSource();

    @Deprecated
    public void setSaltSource(SaltSource saltSource);

    @Deprecated
    public Object getSalt(Customer customer);

    @Deprecated
    public Object getSalt(Customer customer, String unencodedPassword);

    @Deprecated
    public String encodePassword(String rawPassword, Customer customer);

    public String encodePassword(String rawPassword);

    @Deprecated
    public boolean isPasswordValid(String rawPassword, String encodedPassword, Customer customer);

    public boolean isPasswordValid(String rawPassword, String encodedPassword);

}

package com.wakacommerce.profile.core.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wakacommerce.common.audit.Auditable;
import com.wakacommerce.common.copy.MultiTenantCloneable;
import com.wakacommerce.common.locale.domain.Locale;

public interface Customer extends Serializable, MultiTenantCloneable<Customer> {

    public Long getId();

    public void setId(Long id);

    public String getUsername();

    public void setUsername(String username);

    public String getPassword();

    public void setPassword(String password);

    public boolean isPasswordChangeRequired();

    public void setPasswordChangeRequired(boolean passwordChangeRequired);

    public String getRealName();

    public void setRealName(String realName);

    public String getEmailAddress();

    public void setEmailAddress(String emailAddress);

    public ChallengeQuestion getChallengeQuestion();

    public void setChallengeQuestion(ChallengeQuestion challengeQuestion);

    public String getChallengeAnswer();

    public void setChallengeAnswer(String challengeAnswer);

    public String getUnencodedPassword();

    public void setUnencodedPassword(String unencodedPassword);

    public boolean isReceiveEmail();

    public void setReceiveEmail(boolean receiveEmail);

    public boolean isRegistered();

    public void setRegistered(boolean registered);

    public String getUnencodedChallengeAnswer();

    public void setUnencodedChallengeAnswer(String unencodedChallengeAnswer);

    public Auditable getAuditable();

    public void setAuditable(Auditable auditable);
    
    public void setCookied(boolean cookied);
    
    public boolean isCookied();

    public void setLoggedIn(boolean loggedIn);
    
    public boolean isLoggedIn();

    public void setAnonymous(boolean anonymous);
    
    public boolean isAnonymous(); 

    public Locale getCustomerLocale();

    public void setCustomerLocale(Locale customerLocale);
    
    public Map<String, CustomerAttribute> getCustomerAttributes();

    public void setCustomerAttributes(Map<String, CustomerAttribute> customerAttributes);

    public boolean isDeactivated();

    public void setDeactivated(boolean deactivated);

    public List<CustomerAddress> getCustomerAddresses();

    public void setCustomerAddresses(List<CustomerAddress> customerAddresses);

    public List<CustomerPhone> getCustomerPhones();

    public void setCustomerPhones(List<CustomerPhone> customerPhones);

    public List<CustomerPayment> getCustomerPayments();

    public void setCustomerPayments(List<CustomerPayment> customerPayments);

    public Map<String, Object> getTransientProperties();

}

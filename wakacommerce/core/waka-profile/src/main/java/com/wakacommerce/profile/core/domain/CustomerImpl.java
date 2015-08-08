package com.wakacommerce.profile.core.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Where;

import com.wakacommerce.common.admin.domain.AdminMainEntity;
import com.wakacommerce.common.audit.Auditable;
import com.wakacommerce.common.audit.AuditableListener;
import com.wakacommerce.common.copy.CreateResponse;
import com.wakacommerce.common.copy.MultiTenantCopyContext;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.persistence.PreviewStatus;
import com.wakacommerce.common.persistence.Previewable;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.AdminPresentationCollection;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.client.AddMethodType;
import com.wakacommerce.common.presentation.client.VisibilityEnum;

@Entity
@EntityListeners(value = { AuditableListener.class, CustomerPersistedEntityListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CUSTOMER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "blCustomerElements")
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "CustomerImpl_baseCustomer")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.PREVIEW, skipOverlaps=true),
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class CustomerImpl implements Customer, AdminMainEntity, Previewable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CUSTOMER_ID")
    @AdminPresentation(friendlyName = "CustomerImpl_Customer_Id", group = "CustomerImpl_Primary_Key",
            visibility = VisibilityEnum.HIDDEN_ALL)
    protected Long id;

    @Embedded
    protected Auditable auditable = new Auditable();

    @Embedded
    protected PreviewStatus previewable = new PreviewStatus();

    @Column(name = "USER_NAME")
    @AdminPresentation(friendlyName = "CustomerImpl_UserName", order = 4000, group = "CustomerImpl_Customer",
            visibility = VisibilityEnum.HIDDEN_ALL)
    protected String username;

    @Column(name = "PASSWORD")
    @AdminPresentation(excluded = true)
    protected String password;

    @Column(name = "EMAIL_ADDRESS")
    @Index(name = "CUSTOMER_EMAIL_INDEX", columnNames = { "EMAIL_ADDRESS" })
    @AdminPresentation(friendlyName = "CustomerImpl_Email_Address", order = 1000, group = "CustomerImpl_Customer",
            prominent = true, gridOrder = 1000)
    protected String emailAddress;

    @Column(name = "REAL_NAME")
    @AdminPresentation(friendlyName = "CustomerImpl_Real_Name", order = 3000, group = "CustomerImpl_Customer", 
            prominent = true, gridOrder = 3500)
    protected String realName;

    @ManyToOne(targetEntity = ChallengeQuestionImpl.class)
    @JoinColumn(name = "CHALLENGE_QUESTION_ID")
    @Index(name="CUSTOMER_CHALLENGE_INDEX", columnNames={"CHALLENGE_QUESTION_ID"})
    @AdminPresentation(friendlyName = "CustomerImpl_Challenge_Question", order = 4000,
            tab = Presentation.Tab.Name.Advanced, tabOrder = Presentation.Tab.Order.Advanced,
            excluded = true)
    protected ChallengeQuestion challengeQuestion;

    @Column(name = "CHALLENGE_ANSWER")
    @AdminPresentation(friendlyName = "CustomerImpl_Challenge_Answer", order = 5000,
            tab = Presentation.Tab.Name.Advanced, tabOrder = Presentation.Tab.Order.Advanced,
            excluded = true)
    protected String challengeAnswer;

    @Column(name = "PASSWORD_CHANGE_REQUIRED")
    @AdminPresentation(excluded = true)
    protected Boolean passwordChangeRequired = false;

    @Column(name = "RECEIVE_EMAIL")
    @AdminPresentation(friendlyName = "CustomerImpl_Customer_Receive_Email",order=1000, 
            tab = Presentation.Tab.Name.Advanced, tabOrder = Presentation.Tab.Order.Advanced)
    protected Boolean receiveEmail = true;

    @Column(name = "IS_REGISTERED")
    @AdminPresentation(friendlyName = "CustomerImpl_Customer_Registered", order = 4000,
            prominent = true, gridOrder = 4000)
    protected Boolean registered = false;
    
    @Column(name = "DEACTIVATED")
    @AdminPresentation(friendlyName = "CustomerImpl_Customer_Deactivated", order=3000,
        tab = Presentation.Tab.Name.Advanced, tabOrder = Presentation.Tab.Order.Advanced)
    protected Boolean deactivated = false;

    @OneToMany(mappedBy = "customer", targetEntity = CustomerAddressImpl.class, cascade = {CascadeType.ALL})
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @Where(clause = "archived != 'Y'")
    @AdminPresentationCollection(friendlyName = "CustomerImpl_Customer_Addresses", order = 1000,
            addType = AddMethodType.PERSIST,
            tab = Presentation.Tab.Name.Contact, tabOrder = Presentation.Tab.Order.Contact)
    protected List<CustomerAddress> customerAddresses = new ArrayList<CustomerAddress>();

    @OneToMany(mappedBy = "customer", targetEntity = CustomerPhoneImpl.class, cascade = {CascadeType.ALL})
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @AdminPresentationCollection(friendlyName = "CustomerImpl_Customer_Phones", order = 2000,
            addType = AddMethodType.PERSIST,
            tab = Presentation.Tab.Name.Contact, tabOrder = Presentation.Tab.Order.Contact)
    protected List<CustomerPhone> customerPhones = new ArrayList<CustomerPhone>();

    @OneToMany(mappedBy = "customer", targetEntity = CustomerPaymentImpl.class, cascade = {CascadeType.ALL})
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE, region="blStandardElements")
    @BatchSize(size = 50)
    @AdminPresentationCollection(friendlyName = "CustomerImpl_Customer_Payments", order = 3000,
            addType = AddMethodType.PERSIST,
            readOnly = true,
            tab = Presentation.Tab.Name.Contact, tabOrder = Presentation.Tab.Order.Contact)
    protected List<CustomerPayment> customerPayments  = new ArrayList<CustomerPayment>();

    @Transient
    protected String unencodedPassword;

    @Transient
    protected String unencodedChallengeAnswer;
    
    @Transient
    protected boolean anonymous;

    @Transient
    protected boolean cookied;

    @Transient
    protected boolean loggedIn;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isPasswordChangeRequired() {
        return BooleanUtils.toBoolean(passwordChangeRequired);
    }

    @Override
    public void setPasswordChangeRequired(boolean passwordChangeRequired) {
        this.passwordChangeRequired = Boolean.valueOf(passwordChangeRequired);
    }

    @Override
    public String getRealName() {
        return realName;
    }

    @Override
    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Override
    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    @Override
    public ChallengeQuestion getChallengeQuestion() {
        return challengeQuestion;
    }

    @Override
    public void setChallengeQuestion(ChallengeQuestion challengeQuestion) {
        this.challengeQuestion = challengeQuestion;
    }

    @Override
    public String getChallengeAnswer() {
        return challengeAnswer;
    }

    @Override
    public void setChallengeAnswer(String challengeAnswer) {
        this.challengeAnswer = challengeAnswer;
    }

    @Override
    public String getUnencodedPassword() {
        return unencodedPassword;
    }

    @Override
    public void setUnencodedPassword(String unencodedPassword) {
        this.unencodedPassword = unencodedPassword;
    }

    @Override
    public boolean isReceiveEmail() {
        return BooleanUtils.toBoolean(receiveEmail);
    }

    @Override
    public void setReceiveEmail(boolean receiveEmail) {
        this.receiveEmail = Boolean.valueOf(receiveEmail);
    }

    @Override
    public boolean isRegistered() {
        return BooleanUtils.toBoolean(registered);
    }

    @Override
    public void setRegistered(boolean registered) {
        this.registered = Boolean.valueOf(registered);
    }

    @Override
    public String getUnencodedChallengeAnswer() {
        return unencodedChallengeAnswer;
    }

    @Override
    public void setUnencodedChallengeAnswer(String unencodedChallengeAnswer) {
        this.unencodedChallengeAnswer = unencodedChallengeAnswer;
    }

    @Override
    public Auditable getAuditable() {
        return auditable;
    }

    @Override
    public void setAuditable(Auditable auditable) {
        this.auditable = auditable;
    }

    @Override
    public boolean isAnonymous() {
        return anonymous;
    }

    @Override
    public boolean isCookied() {
        return cookied;
    }

    @Override
    public boolean isLoggedIn() {
        return loggedIn;
    }

    @Override
    public void setAnonymous(boolean anonymous) {
        this.anonymous = anonymous;
        if (anonymous) {
            cookied = false;
            loggedIn = false;
        }
    }

    @Override
    public void setCookied(boolean cookied) {
        this.cookied = cookied;
        if (cookied) {
            anonymous = false;
            loggedIn = false;
        }
    }

    @Override
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
        if (loggedIn) {
            anonymous = false;
            cookied = false;
        }
    }

    @Override
    public boolean isDeactivated() {
        return BooleanUtils.toBoolean(deactivated);
    }

    @Override
    public void setDeactivated(boolean deactivated) {
        this.deactivated = Boolean.valueOf(deactivated);
    }

    @Override
    public List<CustomerAddress> getCustomerAddresses() {
        return customerAddresses;
    }

    @Override
    public void setCustomerAddresses(List<CustomerAddress> customerAddresses) {
        this.customerAddresses = customerAddresses;
    }

    @Override
    public List<CustomerPhone> getCustomerPhones() {
        return customerPhones;
    }

    @Override
    public void setCustomerPhones(List<CustomerPhone> customerPhones) {
        this.customerPhones = customerPhones;
    }

    @Override
    public List<CustomerPayment> getCustomerPayments() {
        return customerPayments;
    }

    @Override
    public void setCustomerPayments(List<CustomerPayment> customerPayments) {
        this.customerPayments = customerPayments;
    }

    @Override
    public String getMainEntityName() {
        if (!StringUtils.isEmpty(getRealName())) {
            return getRealName();
        }
        return String.valueOf(getId());
    }

    @Override
    public Boolean getPreview() {
        if (previewable == null) {
            previewable = new PreviewStatus();
        }
        return previewable.getPreview();
    }

    @Override
    public void setPreview(Boolean preview) {
        if (previewable == null) {
            previewable = new PreviewStatus();
        }
        previewable.setPreview(preview);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        CustomerImpl other = (CustomerImpl) obj;

        if (id != null && other.id != null) {
            return id.equals(other.id);
        }

        if (username == null) {
            if (other.username != null) {
                return false;
            }
        } else if (!username.equals(other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public <G extends Customer> CreateResponse<G> createOrRetrieveCopyInstance(MultiTenantCopyContext context) throws CloneNotSupportedException {
        CreateResponse<G> createResponse = context.createOrRetrieveCopyInstance(this);
        if (createResponse.isAlreadyPopulated()) {
            return createResponse;
        }
        Customer cloned = createResponse.getClone();
        cloned.setAnonymous(anonymous);
        cloned.setChallengeAnswer(challengeAnswer);
        cloned.setChallengeQuestion(challengeQuestion);
        cloned.setCookied(cookied);
        for(CustomerAddress entry : customerAddresses){
            CustomerAddress clonedEntry = entry.createOrRetrieveCopyInstance(context).getClone();
            clonedEntry.setCustomer(cloned);
            cloned.getCustomerAddresses().add(clonedEntry);

        }
        cloned.setLoggedIn(loggedIn);
        cloned.setUsername(username);
        cloned.setUnencodedPassword(unencodedPassword);
        cloned.setUnencodedChallengeAnswer(unencodedChallengeAnswer);
        cloned.setRegistered(registered);
        cloned.setReceiveEmail(receiveEmail);
        cloned.setPasswordChangeRequired(passwordChangeRequired);
        cloned.setPassword(password);
        cloned.setRealName(realName);
        cloned.setEmailAddress(emailAddress);
        cloned.setDeactivated(deactivated);
        for(CustomerPayment entry : customerPayments){
            CustomerPayment clonedEntry = entry.createOrRetrieveCopyInstance(context).getClone();
            clonedEntry.setCustomer(cloned);
            cloned.getCustomerPayments().add(clonedEntry);
        }
        for(CustomerPhone entry : customerPhones){
            CustomerPhone clonedEntry = entry.createOrRetrieveCopyInstance(context).getClone();
            clonedEntry.setCustomer(cloned);
            cloned.getCustomerPhones().add(clonedEntry);
        }
        return createResponse;
    }

    public static class Presentation {

        public static class Tab {

            public static class Name {

                public static final String Contact = "CustomerImpl_Contact_Tab";
                public static final String Advanced = "CustomerImpl_Advanced_Tab";
            }

            public static class Order {

                public static final int Contact = 2000;
                public static final int Advanced = 3000;
            }
        }
    }

}

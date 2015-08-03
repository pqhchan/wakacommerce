
package com.wakacommerce.core.payment.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;

import com.wakacommerce.common.currency.domain.BroadleafCurrency;
import com.wakacommerce.common.currency.util.BroadleafCurrencyUtils;
import com.wakacommerce.common.currency.util.CurrencyCodeIdentifiable;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransform;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformMember;
import com.wakacommerce.common.extensibility.jpa.copy.DirectCopyTransformTypes;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentGatewayType;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.persistence.ArchiveStatus;
import com.wakacommerce.common.presentation.AdminPresentation;
import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.AdminPresentationCollection;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeEntry;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeOverride;
import com.wakacommerce.common.presentation.override.AdminPresentationMergeOverrides;
import com.wakacommerce.common.presentation.override.PropertyType;
import com.wakacommerce.core.order.domain.FulfillmentGroupImpl;
import com.wakacommerce.core.order.domain.Order;
import com.wakacommerce.core.order.domain.OrderImpl;
import com.wakacommerce.profile.core.domain.Address;
import com.wakacommerce.profile.core.domain.AddressImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_PAYMENT")
@AdminPresentationMergeOverrides(
    {
        @AdminPresentationMergeOverride(name = "", mergeEntries =
            @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.READONLY,
                                            booleanOverrideValue = true)),
        @AdminPresentationMergeOverride(name = "billingAddress", mergeEntries = {
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.TAB,
                        overrideValue = FulfillmentGroupImpl.Presentation.Tab.Name.Address),
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.TABORDER,
                        intOverrideValue = FulfillmentGroupImpl.Presentation.Tab.Order.Address)
        }),
        @AdminPresentationMergeOverride(name = "billingAddress.isDefault", mergeEntries = {
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.EXCLUDED,
                        booleanOverrideValue = true)
        }),
        @AdminPresentationMergeOverride(name = "billingAddress.isActive", mergeEntries = {
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.EXCLUDED,
                        booleanOverrideValue = true)
        }),
        @AdminPresentationMergeOverride(name = "billingAddress.isBusiness", mergeEntries = {
                @AdminPresentationMergeEntry(propertyType = PropertyType.AdminPresentation.EXCLUDED,
                        booleanOverrideValue = true)
        })
    }
)
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE, friendlyName = "OrderPaymentImpl_baseOrderPayment")
@SQLDelete(sql="UPDATE BLC_ORDER_PAYMENT SET ARCHIVED = 'Y' WHERE ORDER_PAYMENT_ID = ?")
@DirectCopyTransform({
        @DirectCopyTransformMember(templateTokens = DirectCopyTransformTypes.MULTITENANT_SITE)
})
public class OrderPaymentImpl implements OrderPayment, CurrencyCodeIdentifiable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "OrderPaymentId")
    @GenericGenerator(
        name="OrderPaymentId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="OrderPaymentImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.payment.domain.OrderPaymentImpl")
        }
    )
    @Column(name = "ORDER_PAYMENT_ID")
    protected Long id;

    @ManyToOne(targetEntity = OrderImpl.class, optional = true)
    @JoinColumn(name = "ORDER_ID", nullable = true)
    @Index(name="ORDERPAYMENT_ORDER_INDEX", columnNames={"ORDER_ID"})
    @AdminPresentation(excluded = true)
    protected Order order;

    @ManyToOne(targetEntity = AddressImpl.class, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "ADDRESS_ID")
    @Index(name="ORDERPAYMENT_ADDRESS_INDEX", columnNames={"ADDRESS_ID"})
    protected Address billingAddress;

    @Column(name = "AMOUNT", precision=19, scale=5)
    @AdminPresentation(friendlyName = "OrderPaymentImpl_Payment_Amount", order=2000, gridOrder = 2000, prominent=true,
            fieldType=SupportedFieldType.MONEY)
    protected BigDecimal amount;

    @Column(name = "REFERENCE_NUMBER")
    @Index(name="ORDERPAYMENT_REFERENCE_INDEX", columnNames={"REFERENCE_NUMBER"})
    @AdminPresentation(friendlyName = "OrderPaymentImpl_Payment_Reference_Number")
    protected String referenceNumber;

    @Column(name = "PAYMENT_TYPE", nullable = false)
    @Index(name="ORDERPAYMENT_TYPE_INDEX", columnNames={"PAYMENT_TYPE"})
    @AdminPresentation(friendlyName = "OrderPaymentImpl_Payment_Type", order=3000, gridOrder = 3000, prominent=true,
            fieldType= SupportedFieldType.WAKA_ENUMERATION,
            wakaEnumeration="com.wakacommerce.common.payment.PaymentType")
    protected String type;
    
    @Column(name = "GATEWAY_TYPE")
    @AdminPresentation(friendlyName = "OrderPaymentImpl_Gateway_Type", order=1000, gridOrder = 1000, prominent=true,
            fieldType = SupportedFieldType.WAKA_ENUMERATION,
            wakaEnumeration="com.wakacommerce.common.payment.PaymentGatewayType")
    protected String gatewayType;
    
    //TODO: add a filter for archived transactions
    @OneToMany(mappedBy = "orderPayment", targetEntity = PaymentTransactionImpl.class, cascade = { CascadeType.ALL }, orphanRemoval = true)
    @AdminPresentationCollection(friendlyName="OrderPaymentImpl_Details",
            tab = Presentation.Tab.Name.Log, tabOrder = Presentation.Tab.Order.Log)
    protected List<PaymentTransaction> transactions = new ArrayList<PaymentTransaction>();
    
    @Embedded
    protected ArchiveStatus archiveStatus = new ArchiveStatus();
    
    @Override
    public Money getAmount() {
        return amount == null ? null : BroadleafCurrencyUtils.getMoney(amount, getOrder().getCurrency());
    }

    @Override
    public void setAmount(Money amount) {
        this.amount = Money.toAmount(amount);
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Order getOrder() {
        return order;
    }

    @Override
    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public Address getBillingAddress() {
        return billingAddress;
    }

    @Override
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    @Override
    public String getReferenceNumber() {
        return referenceNumber;
    }

    @Override
    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    @Override
    public PaymentType getType() {
        return PaymentType.getInstance(type);
    }

    @Override
    public void setType(PaymentType type) {
        this.type = type == null ? null : type.getType();
    }
    
    @Override
    public PaymentGatewayType getGatewayType() {
        return PaymentGatewayType.getInstance(gatewayType);
    }

    @Override
    public void setPaymentGatewayType(PaymentGatewayType gatewayType) {
        this.gatewayType = gatewayType == null ? null : gatewayType.getType();
    }

    @Override
    public List<PaymentTransaction> getTransactions() {
        return transactions;
    }

    @Override
    public void setTransactions(List<PaymentTransaction> transactions) {
        this.transactions = transactions;
    }
    
    @Override
    public void addTransaction(PaymentTransaction transaction) {
        getTransactions().add(transaction);
    }
    
    @Override
    public List<PaymentTransaction> getTransactionsForType(PaymentTransactionType type) {
        List<PaymentTransaction> result = new ArrayList<PaymentTransaction>();
        for (PaymentTransaction tx : getTransactions()) {
            if (tx.getType().equals(type)) {
                result.add(tx);
            }
        }
        return result;
    }

    @Override
    public PaymentTransaction getInitialTransaction() {
        for (PaymentTransaction tx : getTransactions()) {
            if (tx.getParentTransaction() == null) {
                return tx;
            }
        }
        return null;
    }

    @Override
    public Money getTransactionAmountForType(PaymentTransactionType type) {
        Money amount = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getOrder().getCurrency());
        for (PaymentTransaction tx : getTransactions()){
            if (type.equals(tx.getType())) {
                amount = amount.add(tx.getAmount());
            }
        }
        return amount;
    }
    
    @Override
    public Money getSuccessfulTransactionAmountForType(PaymentTransactionType type) {
        Money amount = BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getOrder().getCurrency());
        for (PaymentTransaction tx : getTransactions()){
            if (type.equals(tx.getType()) && tx.getSuccess()){
                amount = amount.add(tx.getAmount());
            }
        }
        return amount;
    }

    @Override
    public boolean isConfirmed() {
        for (PaymentTransaction tx : getTransactions()){
            if ((PaymentTransactionType.AUTHORIZE_AND_CAPTURE.equals(tx.getType()) ||
                    PaymentTransactionType.AUTHORIZE.equals(tx.getType()))
                    && tx.getSuccess()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isFinalPayment() {
        return PaymentType.CREDIT_CARD.equals(getType()) || PaymentType.THIRD_PARTY_ACCOUNT.equals(getType());
    }

    @Override
    public BroadleafCurrency getCurrency() {
        if (order != null) {
            return order.getCurrency();
        }
        return null;
    }

    @Override
    public String getCurrencyCode() {
        if (getCurrency() != null) {
            return getCurrency().getCurrencyCode();
        }
        return null;
    }
    
    @Override
    public Character getArchived() {
       ArchiveStatus temp;
       if (archiveStatus == null) {
           temp = new ArchiveStatus();
       } else {
           temp = archiveStatus;
       }
       return temp.getArchived();
    }

    @Override
    public void setArchived(Character archived) {
        if (archiveStatus == null) {
            archiveStatus = new ArchiveStatus();
        }
        archiveStatus.setArchived(archived);
    }

    @Override
    public boolean isActive() {
        return 'Y' != getArchived();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj != null && getClass().isAssignableFrom(obj.getClass())) {
            OrderPaymentImpl that = (OrderPaymentImpl) obj;
            return new EqualsBuilder()
                .append(this.id, that.id)
                .append(this.referenceNumber, that.referenceNumber)
                .append(this.type, that.type)
                .append(this.archiveStatus, that.archiveStatus)
                .build();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(referenceNumber)
            .append(type)
            .append(archiveStatus)
            .build();
    }

    public static class Presentation {
        public static class Tab {
            public static class Name {
                public static final String Address = "PaymentInfoImpl_Address_Tab";
                public static final String Log = "PaymentInfoImpl_Log_Tab";
                public static final String Advanced = "PaymentInfoImpl_Advanced_Tab";
            }

            public static class Order {
                public static final int Address = 2000;
                public static final int Log = 4000;
                public static final int Advanced = 5000;
            }
        }

        public static class Group {
            public static class Name {
                public static final String Items = "PaymentInfoImpl_Items";
            }

            public static class Order {
                public static final int Items = 1000;
            }
        }

        public static class FieldOrder {
            public static final int REFNUMBER = 3000;
        }
    }

}

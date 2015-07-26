
package com.wakacommerce.core.payment.dao;

import com.wakacommerce.core.payment.domain.secure.BankAccountPayment;
import com.wakacommerce.core.payment.domain.secure.CreditCardPayment;
import com.wakacommerce.core.payment.domain.secure.GiftCardPayment;
import com.wakacommerce.core.payment.domain.secure.Referenced;

public interface SecureOrderPaymentDao {

    public BankAccountPayment findBankAccountPayment(String referenceNumber);

    public CreditCardPayment findCreditCardPayment(String referenceNumber);

    public GiftCardPayment findGiftCardPayment(String referenceNumber);

    public Referenced save(Referenced securePaymentInfo);

    public BankAccountPayment createBankAccountPayment();

    public GiftCardPayment createGiftCardPayment();

    public CreditCardPayment createCreditCardPayment();

    public void delete(Referenced securePayment);

}


package com.wakacommerce.core.payment.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.encryption.EncryptionModule;
import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.core.payment.domain.secure.BankAccountPayment;
import com.wakacommerce.core.payment.domain.secure.CreditCardPayment;
import com.wakacommerce.core.payment.domain.secure.GiftCardPayment;
import com.wakacommerce.core.payment.domain.secure.Referenced;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository("blSecureOrderPaymentDao")
public class SecureOrderPaymentDaoImpl implements SecureOrderPaymentDao {

    @PersistenceContext(unitName = "blSecurePU")
    protected EntityManager em;

    @Resource(name = "blEncryptionModule")
    protected EncryptionModule encryptionModule;

    @Resource(name = "blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public Referenced save(Referenced securePaymentInfo) {
        return em.merge(securePaymentInfo);
    }

    public BankAccountPayment createBankAccountPayment() {
        BankAccountPayment response = entityConfiguration.createEntityInstance(BankAccountPayment.class.getName(), BankAccountPayment.class);
        response.setEncryptionModule(encryptionModule);
        return response;
    }

    public GiftCardPayment createGiftCardPayment() {
        GiftCardPayment response = entityConfiguration.createEntityInstance(GiftCardPayment.class.getName(), GiftCardPayment.class);
        response.setEncryptionModule(encryptionModule);
        return response;
    }

    public CreditCardPayment createCreditCardPayment() {
        CreditCardPayment response = entityConfiguration.createEntityInstance(CreditCardPayment.class.getName(), CreditCardPayment.class);
        response.setEncryptionModule(encryptionModule);
        return response;
    }

    @SuppressWarnings("unchecked")
    public BankAccountPayment findBankAccountPayment(String referenceNumber) {
        Query query = em.createNamedQuery("BC_READ_BANK_ACCOUNT_BY_REFERENCE_NUMBER");
        query.setParameter("referenceNumber", referenceNumber);
        List<BankAccountPayment> infos = query.getResultList();
        BankAccountPayment response = (infos == null || infos.size() == 0) ? null : infos.get(0);
        if (response != null) {
            response.setEncryptionModule(encryptionModule);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    public CreditCardPayment findCreditCardPayment(String referenceNumber) {
        Query query = em.createNamedQuery("BC_READ_CREDIT_CARD_BY_REFERENCE_NUMBER");
        query.setParameter("referenceNumber", referenceNumber);
        List<CreditCardPayment> infos = query.getResultList();
        CreditCardPayment response = (infos == null || infos.size() == 0) ? null : infos.get(0);
        if (response != null) {
            response.setEncryptionModule(encryptionModule);
        }
        return response;
    }

    @SuppressWarnings("unchecked")
    public GiftCardPayment findGiftCardPayment(String referenceNumber) {
        Query query = em.createNamedQuery("BC_READ_GIFT_CARD_BY_REFERENCE_NUMBER");
        query.setParameter("referenceNumber", referenceNumber);
        List<GiftCardPayment> infos = query.getResultList();
        GiftCardPayment response = (infos == null || infos.size() == 0) ? null : infos.get(0);
        if (response != null) {
            response.setEncryptionModule(encryptionModule);
        }
        return response;
    }

    public void delete(Referenced securePayment) {
        if (!em.contains(securePayment)) {
            securePayment = em.find(securePayment.getClass(), securePayment.getId());
        }
        em.remove(securePayment);
    }

}

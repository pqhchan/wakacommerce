
package com.wakacommerce.core.payment.domain.secure;

import com.wakacommerce.core.payment.service.SecureOrderPaymentService;


/**
 *
 * @ hui
 */
public interface CreditCardPayment extends Referenced {

    @Override
    public Long getId();

    @Override
    public void setId(Long id);

    public String getPan();

    public void setPan(String pan);

    public Integer getExpirationMonth();

    public void setExpirationMonth(Integer expirationMonth);

    public Integer getExpirationYear();

    public void setExpirationYear(Integer expirationYear);

    public String getNameOnCard();

    public void setNameOnCard(String nameOnCard);

    public String getCvvCode();

    public void setCvvCode(String cvvCode);
}

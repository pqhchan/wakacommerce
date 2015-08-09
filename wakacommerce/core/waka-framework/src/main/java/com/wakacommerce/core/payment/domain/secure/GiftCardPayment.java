
package com.wakacommerce.core.payment.domain.secure;

import com.wakacommerce.core.payment.service.SecureOrderPaymentService;


/**
 *
 * @ hui
 */
public interface GiftCardPayment extends Referenced {

    @Override
    public Long getId();

    @Override
    public void setId(Long id);

    public String getPan();

    public void setPan(String pan);

    public String getPin();

    public void setPin(String pin);
}

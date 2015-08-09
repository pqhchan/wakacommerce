
package com.wakacommerce.core.payment.domain.secure;

import com.wakacommerce.core.payment.service.SecureOrderPaymentService;


/**
 *
 * @ hui
 */
public interface BankAccountPayment extends Referenced {

    @Override
    public Long getId();

    @Override
    public void setId(Long id);

    public String getAccountNumber();

    public void setAccountNumber(String accountNumber);

    public String getRoutingNumber();

    public void setRoutingNumber(String routingNumber);

}

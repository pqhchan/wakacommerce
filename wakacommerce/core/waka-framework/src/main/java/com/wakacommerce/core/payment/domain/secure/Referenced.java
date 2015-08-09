
package com.wakacommerce.core.payment.domain.secure;

import com.wakacommerce.common.encryption.EncryptionModule;
import com.wakacommerce.core.payment.domain.OrderPayment;
import com.wakacommerce.core.payment.service.SecureOrderPaymentService;

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 *
 * @ hui
 */
public interface Referenced extends Serializable {

    public Long getId();
    
    public void setId(Long id);

    public String getReferenceNumber();

    public void setReferenceNumber(@Nonnull String referenceNumber);

    public EncryptionModule getEncryptionModule();

    public void setEncryptionModule(EncryptionModule encryptionModule);
    
}

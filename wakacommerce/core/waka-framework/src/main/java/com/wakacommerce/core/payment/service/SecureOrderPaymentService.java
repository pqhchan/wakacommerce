
package com.wakacommerce.core.payment.service;

import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.core.payment.domain.secure.Referenced;
import com.wakacommerce.core.workflow.WorkflowException;

public interface SecureOrderPaymentService {

    public Referenced findSecurePaymentInfo(String referenceNumber, PaymentType paymentType) throws WorkflowException;

    public Referenced save(Referenced securePayment);

    public Referenced create(PaymentType paymentType);

    public void remove(Referenced securePayment);

    public void findAndRemoveSecurePaymentInfo(String referenceNumber, PaymentType paymentType) throws WorkflowException;

}

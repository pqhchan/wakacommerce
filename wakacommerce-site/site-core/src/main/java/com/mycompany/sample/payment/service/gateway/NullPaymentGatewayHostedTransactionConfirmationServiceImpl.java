

package com.mycompany.sample.payment.service.gateway;

import com.mycompany.sample.vendor.nullPaymentGateway.service.payment.NullPaymentGatewayType;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.payment.service.PaymentGatewayTransactionConfirmationService;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Service("blNullPaymentGatewayHostedTransactionConfirmationService")
public class NullPaymentGatewayHostedTransactionConfirmationServiceImpl implements PaymentGatewayTransactionConfirmationService {

    protected static final Log LOG = LogFactory.getLog(NullPaymentGatewayHostedTransactionConfirmationServiceImpl.class);

    @Resource(name = "blNullPaymentGatewayHostedConfiguration")
    protected NullPaymentGatewayHostedConfiguration configuration;

    @Override
    public PaymentResponseDTO confirmTransaction(PaymentRequestDTO paymentRequestDTO) throws PaymentException {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Null Payment Hosted Gateway - Confirming Transaction with amount: " + paymentRequestDTO.getTransactionTotal());
        }

        PaymentTransactionType type = PaymentTransactionType.AUTHORIZE_AND_CAPTURE;
        if (!configuration.isPerformAuthorizeAndCapture()) {
            type = PaymentTransactionType.AUTHORIZE;
        }

        return new PaymentResponseDTO(PaymentType.THIRD_PARTY_ACCOUNT,
                NullPaymentGatewayType.NULL_HOSTED_GATEWAY)
                .rawResponse("confirmation - successful")
                .successful(true)
                .paymentTransactionType(type)
                .amount(new Money(paymentRequestDTO.getTransactionTotal()));
    }

}

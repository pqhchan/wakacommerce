
package com.mycompany.sample.payment.service.gateway;

import com.mycompany.sample.vendor.nullPaymentGateway.service.payment.NullPaymentGatewayConstants;
import com.mycompany.sample.vendor.nullPaymentGateway.service.payment.NullPaymentGatewayType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.payment.service.PaymentGatewayHostedService;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * This is an example implementation of a {@link PaymentGatewayHostedService}.
 * This is just a sample that mimics what URL a real hosted payment gateway implementation
 * might generate. This mimics implementations like PayPal Express Checkout
 *
 * In order to use load this demo service, you will need to component scan
 * the package "com.mycompany.sample".
 *
 * This should NOT be used in production, and is meant solely for demonstration
 * purposes only.
 *
 *Elbert Bautista (elbertbautista)
 */
@Service("blNullPaymentGatewayHostedService")
public class NullPaymentGatewayHostedServiceImpl implements PaymentGatewayHostedService {

    @Resource(name = "blNullPaymentGatewayHostedConfiguration")
    protected NullPaymentGatewayHostedConfiguration configuration;

    @Override
    public PaymentResponseDTO requestHostedEndpoint(PaymentRequestDTO requestDTO) throws PaymentException {
        PaymentResponseDTO responseDTO = new PaymentResponseDTO(PaymentType.THIRD_PARTY_ACCOUNT,
                NullPaymentGatewayType.NULL_HOSTED_GATEWAY)
                .completeCheckoutOnCallback(requestDTO.isCompleteCheckoutOnCallback())
                .responseMap(NullPaymentGatewayConstants.ORDER_ID, requestDTO.getOrderId())
                .responseMap(NullPaymentGatewayConstants.TRANSACTION_AMT, requestDTO.getTransactionTotal())
                .responseMap(NullPaymentGatewayConstants.HOSTED_REDIRECT_URL,
                        configuration.getHostedRedirectUrl());
        return responseDTO;
    }

}

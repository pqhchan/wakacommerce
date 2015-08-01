
package com.mycompany.sample.payment.service.gateway;

import com.mycompany.sample.vendor.nullPaymentGateway.service.payment.NullPaymentGatewayConstants;
import com.mycompany.sample.vendor.nullPaymentGateway.service.payment.NullPaymentGatewayType;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.payment.service.PaymentGatewayWebResponsePrintService;
import com.wakacommerce.common.payment.service.PaymentGatewayWebResponseService;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *  
 */
@Service("blNullPaymentGatewayHostedWebResponseService")
public class NullPaymentGatewayHostedWebResponseServiceImpl implements PaymentGatewayWebResponseService {

    @Resource(name = "blPaymentGatewayWebResponsePrintService")
    protected PaymentGatewayWebResponsePrintService webResponsePrintService;

    @Override
    public PaymentResponseDTO translateWebResponse(HttpServletRequest request) throws PaymentException {
        PaymentResponseDTO responseDTO = new PaymentResponseDTO(PaymentType.THIRD_PARTY_ACCOUNT,
                NullPaymentGatewayType.NULL_HOSTED_GATEWAY)
                .rawResponse(webResponsePrintService.printRequest(request));

        Map<String,String[]> paramMap = request.getParameterMap();

        Money amount = Money.ZERO;
        if (paramMap.containsKey(NullPaymentGatewayConstants.TRANSACTION_AMT)) {
            String amt = paramMap.get(NullPaymentGatewayConstants.TRANSACTION_AMT)[0];
            amount = new Money(amt);
        }

        responseDTO.successful(true)
                .completeCheckoutOnCallback(Boolean.parseBoolean(paramMap.get(NullPaymentGatewayConstants.COMPLETE_CHECKOUT_ON_CALLBACK)[0]))
                .amount(amount)
                .paymentTransactionType(PaymentTransactionType.UNCONFIRMED)
                .orderId(paramMap.get(NullPaymentGatewayConstants.ORDER_ID)[0])
                .responseMap(NullPaymentGatewayConstants.RESULT_MESSAGE,
                        paramMap.get(NullPaymentGatewayConstants.RESULT_MESSAGE)[0]);

        return responseDTO;
    }

}

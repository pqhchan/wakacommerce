

package com.mycompany.sample.payment.service.gateway;

import com.mycompany.sample.vendor.nullPaymentGateway.service.payment.NullPaymentGatewayType;
import com.wakacommerce.common.money.Money;
import com.wakacommerce.common.payment.PaymentTransactionType;
import com.wakacommerce.common.payment.PaymentType;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.payment.service.PaymentGatewayWebResponsePrintService;
import com.wakacommerce.common.payment.service.PaymentGatewayWebResponseService;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

import org.apache.commons.lang.ArrayUtils;

import com.mycompany.sample.vendor.nullPaymentGateway.service.payment.NullPaymentGatewayConstants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 *
 * @ hui
 */
@Service("blNullPaymentGatewayWebResponseService")
public class NullPaymentGatewayWebResponseServiceImpl implements PaymentGatewayWebResponseService {

    @Resource(name = "blPaymentGatewayWebResponsePrintService")
    protected PaymentGatewayWebResponsePrintService webResponsePrintService;

    @Resource(name = "blNullPaymentGatewayConfiguration")
    protected NullPaymentGatewayConfiguration configuration;

    @Override
    public PaymentResponseDTO translateWebResponse(HttpServletRequest request) throws PaymentException {
        PaymentResponseDTO responseDTO = new PaymentResponseDTO(PaymentType.CREDIT_CARD,
                NullPaymentGatewayType.NULL_GATEWAY)
                .rawResponse(webResponsePrintService.printRequest(request));

        Map<String,String[]> paramMap = request.getParameterMap();

        Money amount = Money.ZERO;
        if (paramMap.containsKey(NullPaymentGatewayConstants.TRANSACTION_AMT)) {
            String amt = paramMap.get(NullPaymentGatewayConstants.TRANSACTION_AMT)[0];
            amount = new Money(amt);
        }

        boolean approved = false;
        if (paramMap.containsKey(NullPaymentGatewayConstants.RESULT_SUCCESS)) {
            String[] msg = paramMap.get(NullPaymentGatewayConstants.RESULT_SUCCESS);
            if (ArrayUtils.contains(msg, "true")) {
                approved = true;
            }
        }

        PaymentTransactionType type = PaymentTransactionType.AUTHORIZE_AND_CAPTURE;
        if (!configuration.isPerformAuthorizeAndCapture()) {
            type = PaymentTransactionType.AUTHORIZE;
        }

        responseDTO.successful(approved)
                .amount(amount)
                .paymentTransactionType(type)
                .orderId(paramMap.get(NullPaymentGatewayConstants.ORDER_ID)[0])
                .responseMap(NullPaymentGatewayConstants.GATEWAY_TRANSACTION_ID,
                        paramMap.get(NullPaymentGatewayConstants.GATEWAY_TRANSACTION_ID)[0])
                .responseMap(NullPaymentGatewayConstants.RESULT_MESSAGE,
                        paramMap.get(NullPaymentGatewayConstants.RESULT_MESSAGE)[0])
                .billTo()
                    .addressFirstName(paramMap.get(NullPaymentGatewayConstants.BILLING_FIRST_NAME)[0])
                    .addressLastName(paramMap.get(NullPaymentGatewayConstants.BILLING_LAST_NAME)[0])
                    .addressLine1(paramMap.get(NullPaymentGatewayConstants.BILLING_ADDRESS_LINE1)[0])
                    .addressLine2(paramMap.get(NullPaymentGatewayConstants.BILLING_ADDRESS_LINE2)[0])
                    .addressCityLocality(paramMap.get(NullPaymentGatewayConstants.BILLING_CITY)[0])
                    .addressStateRegion(paramMap.get(NullPaymentGatewayConstants.BILLING_STATE)[0])
                    .addressPostalCode(paramMap.get(NullPaymentGatewayConstants.BILLING_ZIP)[0])
                    .addressCountryCode(paramMap.get(NullPaymentGatewayConstants.BILLING_COUNTRY)[0])
                    .done()
                .creditCard()
                    .creditCardHolderName(paramMap.get(NullPaymentGatewayConstants.CREDIT_CARD_NAME)[0])
                    .creditCardLastFour(paramMap.get(NullPaymentGatewayConstants.CREDIT_CARD_LAST_FOUR)[0])
                    .creditCardType(paramMap.get(NullPaymentGatewayConstants.CREDIT_CARD_TYPE)[0])
                    .creditCardExpDate(paramMap.get(NullPaymentGatewayConstants.CREDIT_CARD_EXP_DATE)[0])
                    .done();

        return responseDTO;

    }


}

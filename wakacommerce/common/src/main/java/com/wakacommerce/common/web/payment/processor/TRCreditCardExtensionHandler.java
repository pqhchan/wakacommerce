

package com.wakacommerce.common.web.payment.processor;

import java.util.Map;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 *
 * @ hui
 */
public interface TRCreditCardExtensionHandler extends ExtensionHandler {

    public ExtensionResultStatusType createTransparentRedirectForm(
            Map<String, Map<String,String>> formParameters,
            PaymentRequestDTO requestDTO,
            Map<String, String> configurationSettings) throws PaymentException;

    public ExtensionResultStatusType setFormActionKey(StringBuilder key);

    public ExtensionResultStatusType setFormHiddenParamsKey(StringBuilder key);

}

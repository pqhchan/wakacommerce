

package com.wakacommerce.common.web.payment.processor;

import java.util.Map;

import com.wakacommerce.common.extension.ExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 *  
 */
public interface TRCreditCardExtensionHandler extends ExtensionHandler {

    /**
     * <p>The implementing modules should take into consideration the passed in configuration settings map
     * and call their implementing TransparentRedirectService to generate either an Authorize
     * or Authorize and Capture Form. The decision should be based on the implementing
     * PaymentGatewayConfiguration.isPerformAuthorizeAndCapture();
     * </p>
     * <p>
     * This method accepts a RequestDTO that represents the order along with a map of
     * gateway-specific configuration settings.
     * The hidden values and the form action will be placed on the passed in formParameters
     * variable. The keys to that map can be retrieved by calling the following methods.
     * getFormActionKey, getFormHiddenParamsKey.
     * </p>
     *
     * @param formParameters
     * @param requestDTO
     * @param configurationSettings
     */
    public ExtensionResultStatusType createTransparentRedirectForm(
            Map<String, Map<String,String>> formParameters,
            PaymentRequestDTO requestDTO,
            Map<String, String> configurationSettings) throws PaymentException;

    public ExtensionResultStatusType setFormActionKey(StringBuilder key);

    public ExtensionResultStatusType setFormHiddenParamsKey(StringBuilder key);

}

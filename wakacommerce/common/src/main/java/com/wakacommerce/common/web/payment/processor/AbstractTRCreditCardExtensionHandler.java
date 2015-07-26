

package com.wakacommerce.common.web.payment.processor;

import java.util.Map;
import javax.annotation.Resource;

import com.wakacommerce.common.extension.AbstractExtensionHandler;
import com.wakacommerce.common.extension.ExtensionResultStatusType;
import com.wakacommerce.common.payment.PaymentGatewayType;
import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.payment.dto.PaymentResponseDTO;
import com.wakacommerce.common.payment.service.PaymentGatewayConfiguration;
import com.wakacommerce.common.payment.service.PaymentGatewayResolver;
import com.wakacommerce.common.payment.service.PaymentGatewayTransparentRedirectService;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

/**
 * <p>An Abstract implementation of the TRCreditCardExtensionHandler.
 * PaymentGateway Handlers will just need to extend this class and implement
 * the declared abstract methods.</p>
 *
 *Elbert Bautista (elbertbautista)
 */
public abstract class AbstractTRCreditCardExtensionHandler extends AbstractExtensionHandler
        implements TRCreditCardExtensionHandler {

    @Resource(name = "blPaymentGatewayResolver")
    protected PaymentGatewayResolver paymentGatewayResolver;

    @Override
    public ExtensionResultStatusType setFormActionKey(StringBuilder key) {
        if (paymentGatewayResolver.isHandlerCompatible(getHandlerType())) {
            key.delete(0, key.length());
            key.append(getFormActionURLKey());
            return ExtensionResultStatusType.HANDLED;
        }

        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType setFormHiddenParamsKey(StringBuilder key) {
        if (paymentGatewayResolver.isHandlerCompatible(getHandlerType())) {
            key.delete(0, key.length());
            key.append(getHiddenParamsKey());
            return ExtensionResultStatusType.HANDLED;
        }

        return ExtensionResultStatusType.NOT_HANDLED;
    }

    @Override
    public ExtensionResultStatusType createTransparentRedirectForm(
            Map<String, Map<String, String>> formParameters,
            PaymentRequestDTO requestDTO,
            Map<String, String> configurationSettings) throws PaymentException {

        if (paymentGatewayResolver.isHandlerCompatible(getHandlerType())) {
            if (formParameters != null && requestDTO != null &&  configurationSettings != null) {
                //Populate any additional configs on the RequestDTO
                for (String config:configurationSettings.keySet()){
                    requestDTO.additionalField(config, configurationSettings.get(config));
                }

                PaymentResponseDTO responseDTO;
                if (getConfiguration().isPerformAuthorizeAndCapture()) {
                    responseDTO = getTransparentRedirectService().createAuthorizeAndCaptureForm(requestDTO);
                } else {
                    responseDTO = getTransparentRedirectService().createAuthorizeForm(requestDTO);
                }

                populateFormParameters(formParameters, responseDTO);

            }

            return ExtensionResultStatusType.HANDLED_CONTINUE;
        }

        return ExtensionResultStatusType.NOT_HANDLED;
    }

    public PaymentGatewayType getHandlerType() {
        return getConfiguration().getGatewayType();
    }

    public abstract String getFormActionURLKey();

    public abstract String getHiddenParamsKey();

    public abstract PaymentGatewayConfiguration getConfiguration();

    public abstract PaymentGatewayTransparentRedirectService getTransparentRedirectService();

    public abstract void populateFormParameters(Map<String, Map<String, String>> formParameters,
                                                PaymentResponseDTO responseDTO);

}

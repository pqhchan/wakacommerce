

package com.wakacommerce.common.web.payment.processor;

import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Attribute;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;

import com.wakacommerce.common.payment.dto.PaymentRequestDTO;
import com.wakacommerce.common.vendor.service.exception.PaymentException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Component("blTransparentRedirectCreditCardFormProcessor")
public class TransparentRedirectCreditCardFormProcessor extends AbstractElementProcessor {

    @Resource(name = "blTRCreditCardExtensionManager")
    protected TRCreditCardExtensionManager extensionManager;

    public TransparentRedirectCreditCardFormProcessor() {
        super("transparent_credit_card_form");
    }

    @Override
    public int getPrecedence() {
        return 1;
    }

    @Override
    protected ProcessorResult processElement(Arguments arguments, Element element) {
        Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                .parseExpression(arguments.getConfiguration(), arguments, element.getAttributeValue("paymentRequestDTO"));
        PaymentRequestDTO requestDTO = (PaymentRequestDTO) expression.execute(arguments.getConfiguration(), arguments);

        element.removeAttribute("paymentRequestDTO");

        Map<String, Map<String,String>> formParameters = new HashMap<String, Map<String,String>>();
        Map<String, String> configurationSettings = new HashMap<String, String>();

        //Create the configuration settings map to pass into the payment module
        Map<String, Attribute> attributeMap  = element.getAttributeMap();
        List<String> keysToRemove = new ArrayList<String>();
        for (String key : attributeMap.keySet()) {
            if (key.startsWith("config-")){
                final int trimLength = "config-".length();
                String configParam = key.substring(trimLength);
                configurationSettings.put(configParam, attributeMap.get(key).getValue());
                keysToRemove.add(key);
            }
        }

        for (String keyToRemove : keysToRemove) {
            element.removeAttribute(keyToRemove);
        }

        try {
            extensionManager.getProxy().createTransparentRedirectForm(formParameters,
                    requestDTO, configurationSettings);
        } catch (PaymentException e) {
            throw new RuntimeException("Unable to Create the Transparent Redirect Form", e);
        }

        StringBuilder formActionKey = new StringBuilder("formActionKey");
        StringBuilder formHiddenParamsKey = new StringBuilder("formHiddenParamsKey");
        extensionManager.getProxy().setFormActionKey(formActionKey);
        extensionManager.getProxy().setFormHiddenParamsKey(formHiddenParamsKey);

        //Change the action attribute on the form to the Payment Gateways Endpoint
        String actionUrl = "";
        Map<String,String> actionValue = formParameters.get(formActionKey.toString());
        if (actionValue != null && actionValue.size()>0) {
            String key = (String)actionValue.keySet().toArray()[0];
            actionUrl = actionValue.get(key);
        }
        element.setAttribute("action", actionUrl);

        //Append any hidden fields necessary for the Transparent Redirect
        Map<String, String> hiddenFields = formParameters.get(formHiddenParamsKey.toString());
        if (hiddenFields != null && !hiddenFields.isEmpty()) {
            for (String key : hiddenFields.keySet()) {
                Element hiddenNode = new Element("input");
                hiddenNode.setAttribute("type", "hidden");
                hiddenNode.setAttribute("name", key);
                hiddenNode.setAttribute("value", hiddenFields.get(key));
                element.addChild(hiddenNode);
            }
        }

        // Convert the <blc:transparent_credit_card_form> node to a normal <form> node
        Element newElement = element.cloneElementNodeWithNewName(element.getParent(), "form", false);
        newElement.setRecomputeProcessorsImmediately(true);
        element.getParent().insertAfter(element, newElement);
        element.getParent().removeChild(element);

        return ProcessorResult.OK;
    }

    public TRCreditCardExtensionManager getExtensionManager() {
        return extensionManager;
    }

    public void setExtensionManager(TRCreditCardExtensionManager extensionManager) {
        this.extensionManager = extensionManager;
    }
}

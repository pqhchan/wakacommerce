

package com.wakacommerce.common.web.payment.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.element.AbstractLocalVariableDefinitionElementProcessor;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
@Component("blCreditCardTypesProcessor")
public class CreditCardTypesProcessor extends AbstractLocalVariableDefinitionElementProcessor {

    protected static final Log LOG = LogFactory.getLog(CreditCardTypesProcessor.class);

    @Resource(name = "blCreditCardTypesExtensionManager")
    protected CreditCardTypesExtensionManager extensionManager;

    public CreditCardTypesProcessor() {
        super("credit_card_types");
    }

    @Override
    public int getPrecedence() {
        return 100;
    }

    @Override
    protected boolean removeHostElement(Arguments arguments, Element element) {
        return false;
    }

    @Override
    protected Map<String, Object> getNewLocalVariables(Arguments arguments, Element element) {
        Map<String, Object> localVars = new HashMap<String, Object>();

        Map<String, String> creditCardTypes = new HashMap<String, String>();

        try {
            extensionManager.getProxy().populateCreditCardMap(creditCardTypes);
        } catch (Exception e) {
            LOG.warn("Unable to Populate Credit Card Types Map for this Payment Module, or card type is not needed.");
        }

        if (!creditCardTypes.isEmpty()) {
            localVars.put("paymentGatewayCardTypes", creditCardTypes);
        }

        return localVars;
    }



}

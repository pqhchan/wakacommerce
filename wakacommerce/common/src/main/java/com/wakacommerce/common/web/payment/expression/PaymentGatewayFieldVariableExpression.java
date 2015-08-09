

package com.wakacommerce.common.web.payment.expression;

import javax.annotation.Resource;

import com.wakacommerce.common.web.expression.BroadleafVariableExpression;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
public class PaymentGatewayFieldVariableExpression implements BroadleafVariableExpression {

    @Resource(name = "blPaymentGatewayFieldExtensionManager")
    protected PaymentGatewayFieldExtensionManager extensionManager;

    @Override
    public String getName() {
        return "paymentGatewayField";
    }

    public String mapName(String fieldName) {
        Map<String, String> fieldNameMap = new HashMap<String, String>();
        fieldNameMap.put(fieldName, fieldName);
        extensionManager.getProxy().mapFieldName(fieldName, fieldNameMap);
        return fieldNameMap.get(fieldName);
    }

    public PaymentGatewayFieldExtensionManager getExtensionManager() {
        return extensionManager;
    }

    public void setExtensionManager(PaymentGatewayFieldExtensionManager extensionManager) {
        this.extensionManager = extensionManager;
    }
}

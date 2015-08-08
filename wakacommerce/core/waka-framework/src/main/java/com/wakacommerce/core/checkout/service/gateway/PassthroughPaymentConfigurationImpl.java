  
package com.wakacommerce.core.checkout.service.gateway;

import org.springframework.stereotype.Service;

import com.wakacommerce.common.payment.PaymentGatewayType;
import com.wakacommerce.common.payment.service.PaymentGatewayConfiguration;

/**
 * A Default Configuration to handle Passthrough Payments, for example COD payments.
 *  
 */
@Service("blPassthroughPaymentConfiguration")
public class PassthroughPaymentConfigurationImpl implements PaymentGatewayConfiguration {

    protected int failureReportingThreshold = 0;

    protected boolean performAuthorizeAndCapture = true;

    @Override
    public boolean isPerformAuthorizeAndCapture() {
        return false;
    }

    @Override
    public void setPerformAuthorizeAndCapture(boolean performAuthorizeAndCapture) {
        this.performAuthorizeAndCapture = performAuthorizeAndCapture;
    }

    @Override
    public int getFailureReportingThreshold() {
        return failureReportingThreshold;
    }

    @Override
    public void setFailureReportingThreshold(int failureReportingThreshold) {
        this.failureReportingThreshold = failureReportingThreshold;
    }

    @Override
    public boolean handlesAuthorize() {
        return true;
    }

    @Override
    public boolean handlesCapture() {
        return false;
    }

    @Override
    public boolean handlesAuthorizeAndCapture() {
        return true;
    }

    @Override
    public boolean handlesReverseAuthorize() {
        return false;
    }

    @Override
    public boolean handlesVoid() {
        return false;
    }

    @Override
    public boolean handlesRefund() {
        return false;
    }

    @Override
    public boolean handlesPartialCapture() {
        return false;
    }

    @Override
    public boolean handlesMultipleShipment() {
        return false;
    }

    @Override
    public boolean handlesRecurringPayment() {
        return false;
    }

    @Override
    public boolean handlesSavedCustomerPayment() {
        return false;
    }

    @Override
    public boolean handlesMultiplePayments() {
        return false;
    }

    @Override
    public PaymentGatewayType getGatewayType() {
        return PaymentGatewayType.PASSTHROUGH;
    }

}

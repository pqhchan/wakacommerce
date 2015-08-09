

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.payment.PaymentGatewayType;

/**
 *
 * @ hui
 */
public interface PaymentGatewayConfiguration {

    public boolean isPerformAuthorizeAndCapture();

    public void setPerformAuthorizeAndCapture(boolean performAuthorizeAndCapture);

    public int getFailureReportingThreshold();

    public void setFailureReportingThreshold(int failureReportingThreshold);

    public boolean handlesAuthorize();

    public boolean handlesCapture();

    public boolean handlesAuthorizeAndCapture();

    public boolean handlesReverseAuthorize();

    public boolean handlesVoid();

    public boolean handlesRefund();

    public boolean handlesPartialCapture();

    public boolean handlesMultipleShipment();

    public boolean handlesRecurringPayment();

    public boolean handlesSavedCustomerPayment();

    public boolean handlesMultiplePayments();

    public PaymentGatewayType getGatewayType();

}

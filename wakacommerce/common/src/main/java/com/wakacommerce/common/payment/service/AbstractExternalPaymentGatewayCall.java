

package com.wakacommerce.common.payment.service;

import com.wakacommerce.common.vendor.service.exception.PaymentException;
import com.wakacommerce.common.vendor.service.monitor.ServiceStatusDetectable;
import com.wakacommerce.common.vendor.service.type.ServiceStatusType;

/**
 *
 * @ hui
 */
public abstract class AbstractExternalPaymentGatewayCall<T,R> implements ServiceStatusDetectable<T> {

    protected Integer failureCount = 0;
    protected Boolean isUp = true;

    protected synchronized void clearStatus() {
        isUp = true;
        failureCount = 0;
    }

    protected synchronized void incrementFailure() {
        if (failureCount >= getFailureReportingThreshold()) {
            isUp = false;
        } else {
            failureCount++;
        }
    }

    @Override
    public synchronized ServiceStatusType getServiceStatus() {
        if (isUp) {
            return ServiceStatusType.UP;
        } else {
            return ServiceStatusType.DOWN;
        }
    }

    @Override
    public R process(T paymentRequest) throws PaymentException {
        R response;
        try {
            response = communicateWithVendor(paymentRequest);
        } catch (Exception e) {
            incrementFailure();
            throw new PaymentException(e);
        }
        clearStatus();

        return response;
    }

    public abstract R communicateWithVendor(T paymentRequest) throws Exception;

    public abstract Integer getFailureReportingThreshold();

}

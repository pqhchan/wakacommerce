
package com.wakacommerce.common.vendor.service.monitor;

import com.wakacommerce.common.vendor.service.type.ServiceStatusType;

public interface StatusHandler {

    public void handleStatus(String serviceName, ServiceStatusType status);

}

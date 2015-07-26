
package com.wakacommerce.common.vendor.service.monitor.handler;

import java.util.ArrayList;
import java.util.List;

import com.wakacommerce.common.vendor.service.monitor.StatusHandler;
import com.wakacommerce.common.vendor.service.type.ServiceStatusType;

public class CompositeStatusHandler implements StatusHandler {

    protected List<StatusHandler> handlers = new ArrayList<StatusHandler>();

    public void handleStatus(String serviceName, ServiceStatusType status) {
        for (StatusHandler statusHandler : handlers) {
            statusHandler.handleStatus(serviceName, status);
        }
    }

    public List<StatusHandler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<StatusHandler> handlers) {
        this.handlers = handlers;
    }

}

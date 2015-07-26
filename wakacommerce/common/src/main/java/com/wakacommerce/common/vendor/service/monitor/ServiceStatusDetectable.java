
package com.wakacommerce.common.vendor.service.monitor;

import java.io.Serializable;

import com.wakacommerce.common.vendor.service.type.ServiceStatusType;

public interface ServiceStatusDetectable<T> {

    public ServiceStatusType getServiceStatus();

    public String getServiceName();
    
    public Object process(T arg) throws Exception;

}

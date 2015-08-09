
package com.wakacommerce.common.jmx;

import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

/**
 *
 * @ hui
 */
public class MetadataNamingStrategy extends org.springframework.jmx.export.naming.MetadataNamingStrategy {

    public ObjectName getObjectName(Object managedBean, String beanKey) throws MalformedObjectNameException {
        managedBean = AspectUtil.exposeRootBean(managedBean);
        return super.getObjectName(managedBean, beanKey);
    }

}

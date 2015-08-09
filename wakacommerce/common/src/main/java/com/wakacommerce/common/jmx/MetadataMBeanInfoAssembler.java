
package com.wakacommerce.common.jmx;

import javax.management.Descriptor;
import javax.management.JMException;
import javax.management.modelmbean.ModelMBeanAttributeInfo;
import javax.management.modelmbean.ModelMBeanNotificationInfo;
import javax.management.modelmbean.ModelMBeanOperationInfo;

/**
 *
 * @ hui
 */
public class MetadataMBeanInfoAssembler extends org.springframework.jmx.export.assembler.MetadataMBeanInfoAssembler {

    protected void checkManagedBean(Object managedBean) throws IllegalArgumentException {
        //do nothing
    }

    protected ModelMBeanNotificationInfo[] getNotificationInfo(Object managedBean, String beanKey) {
        managedBean = AspectUtil.exposeRootBean(managedBean);
        return super.getNotificationInfo(managedBean, beanKey);
    }

    protected void populateMBeanDescriptor(Descriptor desc, Object managedBean, String beanKey) {
        managedBean = AspectUtil.exposeRootBean(managedBean);
        super.populateMBeanDescriptor(desc, managedBean, beanKey);
    }

    protected ModelMBeanAttributeInfo[] getAttributeInfo(Object managedBean, String beanKey) throws JMException {
        managedBean = AspectUtil.exposeRootBean(managedBean);
        return super.getAttributeInfo(managedBean, beanKey);
    }

    protected ModelMBeanOperationInfo[] getOperationInfo(Object managedBean, String beanKey) {
        managedBean = AspectUtil.exposeRootBean(managedBean);
        return super.getOperationInfo(managedBean, beanKey);
    }
}

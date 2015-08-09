
package com.wakacommerce.common.extensibility.context.merge;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.beans.factory.config.SetFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 *
 * @ hui
 */
public abstract class AbstractRemoveBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {

    protected String beanRef;
    protected String targetRef;
    protected String mapKey;
    protected String mapKeyRef;
    protected ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals(targetRef)) {
            if (bean instanceof ListFactoryBean || bean instanceof List) {
                Object beanToRemove = applicationContext.getBean(beanRef);
                try {
                    List sourceList;
                    if (bean instanceof ListFactoryBean) {
                        Field field = ListFactoryBean.class.getDeclaredField("sourceList");
                        field.setAccessible(true);
                        sourceList = (List) field.get(bean);
                    } else {
                        sourceList = (List) bean;
                    }
                    Iterator itr = sourceList.iterator();
                    while (itr.hasNext()) {
                        Object member = itr.next();
                        if (member.equals(beanToRemove)) {
                            itr.remove();
                        }
                    }
                } catch (Exception e) {
                    throw new BeanCreationException(e.getMessage());
                }
            } else if (bean instanceof SetFactoryBean || bean instanceof Set) {
                Object beanToRemove = applicationContext.getBean(beanRef);
                try {
                    Set sourceSet;
                    if (bean instanceof SetFactoryBean) {
                        Field field = SetFactoryBean.class.getDeclaredField("sourceSet");
                        field.setAccessible(true);
                        sourceSet = (Set) field.get(bean);
                    } else {
                        sourceSet = (Set)bean;
                    }
                    List tempList = new ArrayList(sourceSet);
                    Iterator itr = tempList.iterator();
                    while (itr.hasNext()) {
                        Object member = itr.next();
                        if (member.equals(beanToRemove)) {
                            itr.remove();
                        }
                    }
                    sourceSet.clear();
                    sourceSet.addAll(tempList);
                } catch (Exception e) {
                    throw new BeanCreationException(e.getMessage());
                }
            } else if (bean instanceof MapFactoryBean || bean instanceof Map) {
                try {
                    Map sourceMap;
                    if (bean instanceof MapFactoryBean) {
                        Field field = MapFactoryBean.class.getDeclaredField("sourceMap");
                        field.setAccessible(true);
                        sourceMap = (Map) field.get(bean);
                    } else {
                        sourceMap = (Map) bean;
                    }
                    Object key;
                    if (mapKey != null) {
                        key = mapKey;
                    } else {
                        key = applicationContext.getBean(mapKeyRef);
                    }
                    Map referenceMap = new LinkedHashMap(sourceMap);
                    for (Object sourceKey : referenceMap.keySet()) {
                        if (sourceKey.equals(key)) {
                            sourceMap.remove(key);
                        }
                    }
                } catch (Exception e) {
                    throw new BeanCreationException(e.getMessage());
                }
            } else {
                throw new IllegalArgumentException("Bean (" + beanName + ") is specified as a merge target, " +
                        "but is not" +
                        " of type ListFactoryBean, SetFactoryBean or MapFactoryBean");
            }
        }

        return bean;
    }

    public String getBeanRef() {
        return beanRef;
    }

    public void setBeanRef(String beanRef) {
        this.beanRef = beanRef;
    }

    public String getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(String targetRef) {
        this.targetRef = targetRef;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public String getMapKeyRef() {
        return mapKeyRef;
    }

    public void setMapKeyRef(String mapKeyRef) {
        this.mapKeyRef = mapKeyRef;
    }
}

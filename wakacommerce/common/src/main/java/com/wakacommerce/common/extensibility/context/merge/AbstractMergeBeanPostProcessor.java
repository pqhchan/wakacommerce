
package com.wakacommerce.common.extensibility.context.merge;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.ListFactoryBean;
import org.springframework.beans.factory.config.MapFactoryBean;
import org.springframework.beans.factory.config.SetFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @ hui
 */
public abstract class AbstractMergeBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {
    protected static final Log LOG = LogFactory.getLog(AbstractMergeBeanPostProcessor.class);

    protected String collectionRef;
    protected String targetRef;
    protected Placement placement = Placement.APPEND;
    protected int position;
    protected ApplicationContext applicationContext;
    protected MergeBeanStatusProvider statusProvider;

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
        if (statusProvider != null && !statusProvider.isProcessingEnabled(bean, beanName, applicationContext)) {
            if (LOG.isTraceEnabled()) {
                LOG.trace(String.format("Not performing post-processing on targetRef [%s] because the registered " +
                		"status provider [%s] returned false", targetRef, statusProvider.getClass().getSimpleName()));
            }
            
            return bean;
        }
        
        if (beanName.equals(targetRef)) {
            Object mergeCollection = applicationContext.getBean(collectionRef);
            if (bean instanceof ListFactoryBean || bean instanceof List) {
                try {
                    List mergeList = (List) mergeCollection;
                    List sourceList;
                    if (bean instanceof ListFactoryBean) {
                        Field field = ListFactoryBean.class.getDeclaredField("sourceList");
                        field.setAccessible(true);
                        sourceList = (List) field.get(bean);
                    } else {
                        sourceList = (List) bean;
                    }
                    switch (placement) {
                        case APPEND:
                            sourceList.addAll(mergeList);
                            break;
                        case PREPEND:
                            sourceList.addAll(0, mergeList);
                            break;
                        case SPECIFIC:
                            sourceList.addAll(position, mergeList);
                            break;
                    }
                } catch (Exception e) {
                    throw new BeanCreationException(e.getMessage());
                }
            } else if (bean instanceof SetFactoryBean || bean instanceof Set) {
                try {
                    Set mergeSet = (Set) mergeCollection;
                    Set sourceSet;
                    if (bean instanceof SetFactoryBean) {
                        Field field = SetFactoryBean.class.getDeclaredField("sourceSet");
                        field.setAccessible(true);
                        sourceSet = (Set) field.get(bean);
                    } else {
                        sourceSet = (Set)bean;
                    }
                    List tempList = new ArrayList(sourceSet);
                    switch (placement) {
                        case APPEND:
                            tempList.addAll(mergeSet);
                            break;
                        case PREPEND:
                            tempList.addAll(0, mergeSet);
                            break;
                        case SPECIFIC:
                            tempList.addAll(position, mergeSet);
                            break;
                    }
                    sourceSet.clear();
                    sourceSet.addAll(tempList);
                } catch (Exception e) {
                    throw new BeanCreationException(e.getMessage());
                }
            } else if (bean instanceof MapFactoryBean || bean instanceof Map) {
                try {
                    Map mergeMap = (Map) mergeCollection;
                    Map sourceMap;
                    if (bean instanceof MapFactoryBean) {
                        Field field = MapFactoryBean.class.getDeclaredField("sourceMap");
                        field.setAccessible(true);
                        sourceMap = (Map) field.get(bean);
                    } else {
                        sourceMap = (Map) bean;
                    }
                    LinkedHashMap tempMap = new LinkedHashMap();
                    switch (placement) {
                        case APPEND:
                            tempMap.putAll(sourceMap);
                            tempMap.putAll(mergeMap);
                            break;
                        case PREPEND:
                            tempMap.putAll(mergeMap);
                            tempMap.putAll(sourceMap);
                            break;
                        case SPECIFIC:
                            boolean added = false;
                            int j = 0;
                            for (Object key : sourceMap.keySet()) {
                                if (j == position) {
                                    tempMap.putAll(mergeMap);
                                    added = true;
                                }
                                tempMap.put(key, sourceMap.get(key));
                                j++;
                            }
                            if (!added) {
                                tempMap.putAll(mergeMap);
                            }
                            break;
                    }
                    sourceMap.clear();
                    sourceMap.putAll(tempMap);
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

    public String getCollectionRef() {
        return collectionRef;
    }

    public void setCollectionRef(String collectionRef) {
        this.collectionRef = collectionRef;
    }

    public String getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(String targetRef) {
        this.targetRef = targetRef;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public MergeBeanStatusProvider getStatusProvider() {
        return statusProvider;
    }

    public void setStatusProvider(MergeBeanStatusProvider statusProvider) {
        this.statusProvider = statusProvider;
    }
    
}

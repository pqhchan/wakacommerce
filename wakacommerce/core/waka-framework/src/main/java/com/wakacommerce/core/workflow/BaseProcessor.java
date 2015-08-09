
package com.wakacommerce.core.workflow;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.OrderComparator;

import com.wakacommerce.common.logging.LifeCycleEvent;
import com.wakacommerce.common.logging.SupportLogManager;
import com.wakacommerce.common.logging.SupportLogger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @ hui
 */
public abstract class BaseProcessor implements InitializingBean, BeanNameAware, BeanFactoryAware, Processor {

    protected BeanFactory beanFactory;
    protected String beanName;
    protected List<Activity<ProcessContext<? extends Object>>> activities = new ArrayList<Activity<ProcessContext<? extends Object>>>();
    protected List<ModuleActivity> moduleActivities = new ArrayList<ModuleActivity>();
    
    protected ErrorHandler defaultErrorHandler;

    @Value("${workflow.auto.rollback.on.error}")
    private boolean autoRollbackOnError = true;

    protected boolean allowEmptyActivities = false;
    
    protected SupportLogger supportLogger = SupportLogManager.getLogger("Workflows", BaseProcessor.class);

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;

    }

    /** Sets the spring bean factroy bean that is responsible for this processor.
     * @see org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    public boolean getAutoRollbackOnError() {
        return autoRollbackOnError;
    }

    public void setAutoRollbackOnError(boolean autoRollbackOnError) {
        this.autoRollbackOnError = autoRollbackOnError;
    }

    public boolean isAllowEmptyActivities() {
        return allowEmptyActivities;
    }

    public void setAllowEmptyActivities(boolean allowEmptyActivities) {
        this.allowEmptyActivities = allowEmptyActivities;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        if (!(beanFactory instanceof ListableBeanFactory)) {
            throw new BeanInitializationException("The workflow processor ["+beanName+"] " +
                    "is not managed by a ListableBeanFactory, please re-deploy using some derivative of ListableBeanFactory such as" +
            "ClassPathXmlApplicationContext ");
        }

        if (CollectionUtils.isEmpty(activities) && !isAllowEmptyActivities()) {
            throw new UnsatisfiedDependencyException(getBeanDesc(), beanName, "activities",
            "No activities were wired for this workflow");
        }
        
        //sort the activities based on their configured order
        OrderComparator.sort(activities);

        HashSet<String> moduleNames = new HashSet<String>();
        for (Iterator<Activity<ProcessContext<? extends Object>>> iter = activities.iterator(); iter.hasNext();) {
            Activity<? extends ProcessContext<? extends Object>> activity = iter.next();
            if ( !supports(activity)) {
                throw new BeanInitializationException("The workflow processor ["+beanName+"] does " +
                        "not support the activity of type"+activity.getClass().getName());
            }
            
            if (activity instanceof ModuleActivity) {
                moduleActivities.add((ModuleActivity) activity);
                moduleNames.add(((ModuleActivity) activity).getModuleName());
            }
        }
        
        if (CollectionUtils.isNotEmpty(moduleActivities)) {
            //log the fact that we've got some modifications to the workflow
            StringBuffer message = new StringBuffer();
            message.append("The following modules have made changes to the " + getBeanName() + " workflow: ");
            message.append(Arrays.toString(moduleNames.toArray()));
            message.append("\n");            
            message.append("The final ordering of activities for the " + getBeanName() + " workflow is: \n");
            ArrayList<String> activityNames = new ArrayList<String>();
            CollectionUtils.collect(activities, new Transformer() {

                @Override
                public Object transform(Object input) {
                    return ((Activity) input).getBeanName();
                }
            }, activityNames);
            message.append(Arrays.toString(activityNames.toArray()));

            supportLogger.lifecycle(LifeCycleEvent.CONFIG, message.toString());
        }
        
    }

    protected String getBeanDesc() {
        return (beanFactory instanceof ConfigurableListableBeanFactory) ?
                ((ConfigurableListableBeanFactory) beanFactory).getBeanDefinition(beanName).getResourceDescription()
                : "Workflow Processor: " + beanName;
    }

    @Override
    public void setActivities(List<Activity<ProcessContext<? extends Object>>> activities) {
        this.activities = activities;
    }

    @Override
    public void setDefaultErrorHandler(ErrorHandler defaultErrorHandler) {
        this.defaultErrorHandler = defaultErrorHandler;
    }

    public List<Activity<ProcessContext<? extends Object>>> getActivities() {
        return activities;
    }

    public List<ModuleActivity> getModuleActivities() {
        return moduleActivities;
    }

    public String getBeanName() {
        return beanName;
    }

    public ErrorHandler getDefaultErrorHandler() {
        return defaultErrorHandler;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }
}

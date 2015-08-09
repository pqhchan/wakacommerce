
package com.wakacommerce.common.security.channel;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.security.web.access.channel.ChannelDecisionManagerImpl;
import org.springframework.security.web.access.channel.ChannelProcessor;
import org.springframework.security.web.access.channel.InsecureChannelProcessor;
import org.springframework.security.web.access.channel.SecureChannelProcessor;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.ServletRequest;

/**
 *
 * @ hui
 */
public class ProtoChannelBeanPostProcessor implements BeanPostProcessor, Ordered {

    Log LOG = LogFactory.getLog(ProtoChannelBeanPostProcessor.class);
    
    protected List<ChannelProcessor> channelProcessorOverrides;
    
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ChannelDecisionManagerImpl) {
            try {
                ChannelDecisionManagerImpl manager = (ChannelDecisionManagerImpl) bean;
                Field channelProcessors = manager.getClass().getDeclaredField("channelProcessors");
                channelProcessors.setAccessible(true);
                List<ChannelProcessor> list = (List<ChannelProcessor>) channelProcessors.get(manager);
                list.clear();
                manager.setChannelProcessors(channelProcessorOverrides);
                LOG.info("Replacing the standard Spring Security channel processors with custom processors that look for a " +
                		"'X-Forwarded-Proto' request header. This allows Spring Security to sit behind a load balancer with SSL termination.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return bean;
    }

    @Override
    public int getOrder() {
        return 9999;
    }

    public List<ChannelProcessor> getChannelProcessorOverrides() {
        return channelProcessorOverrides;
    }

    public void setChannelProcessorOverrides(List<ChannelProcessor> channelProcessorOverrides) {
        this.channelProcessorOverrides = channelProcessorOverrides;
    }
    
}

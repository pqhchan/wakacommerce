package com.wakacommerce.common.web.extensibility;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.beans.BeansException;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.wakacommerce.common.classloader.release.ThreadLocalManager;

public class MergeContextLoader extends ContextLoaderListener {

    /**
     * Name of servlet context parameter (i.e., "<code>patchConfigLocation</code>")
     * that can specify the config location for the rootId context.
     */
    public static final String PATCH_LOCATION_PARAM = "patchConfigLocation";
    
    /**
     * Name of a bean to hook before Spring shutdown for this
     * context commences.
     */
    public static final String SHUTDOWN_HOOK_BEAN = "shutdownHookBean";
    
    /**
     * Name of method to call on the shutdown hook bean before
     * Spring shutdown for this context commences
     */
    public static final String SHUTDOWN_HOOK_METHOD = "shutdownHookMethod";

    /**
     * Instantiate the rootId WebApplicationContext for this loader, either the
     * default context class or a custom context class if specified.
     * <p>This implementation expects custom contexts to implement the
     * {@link ConfigurableWebApplicationContext} interface.
     * Can be overridden in subclasses.
     * <p>In addition, {@link #customizeContext} gets called prior to refreshing the
     * context, allowing subclasses to perform custom modifications to the context.
     * @param servletContext current servlet context
     * @return the rootId WebApplicationContext
     * @throws BeansException if the context couldn't be initialized
     * @see ConfigurableWebApplicationContext
     */
    @Override
    protected WebApplicationContext createWebApplicationContext(ServletContext servletContext) throws BeansException {
        MergeXmlWebApplicationContext wac = new MergeXmlWebApplicationContext();
        wac.setServletContext(servletContext);
        wac.setConfigLocation(servletContext.getInitParameter(ContextLoader.CONFIG_LOCATION_PARAM));
        wac.setPatchLocation(servletContext.getInitParameter(PATCH_LOCATION_PARAM));
        wac.setShutdownBean(servletContext.getInitParameter(SHUTDOWN_HOOK_BEAN));
        wac.setShutdownMethod(servletContext.getInitParameter(SHUTDOWN_HOOK_METHOD));
        customizeContext(servletContext, wac);
 
        return wac;
    }
    

    @Override
    public void contextInitialized(ServletContextEvent event) {
        super.contextInitialized(event);
        ThreadLocalManager.remove();
    }

}

package com.wakacommerce.common.config;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.StringValueResolver;

import com.wakacommerce.common.logging.SupportLogManager;
import com.wakacommerce.common.logging.SupportLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

/**
 *
 * @ hui
 */
public class RuntimeEnvironmentPropertiesConfigurer extends PropertyPlaceholderConfigurer implements InitializingBean {

    private static final Log LOG = LogFactory.getLog(RuntimeEnvironmentPropertiesConfigurer.class);
    protected SupportLogger logger = SupportLogManager.getLogger("UserOverride", this.getClass());
    
    protected static final String SHARED_PROPERTY_OVERRIDE = "property-shared-override";
    protected static final String PROPERTY_OVERRIDE = "property-override";
    
    protected static Set<String> defaultEnvironments = new LinkedHashSet<String>();
    protected static Set<Resource> blcPropertyLocations = new LinkedHashSet<Resource>();
    protected static Set<Resource> defaultPropertyLocations = new LinkedHashSet<Resource>();
    
    static {
        defaultEnvironments.add("production");
        defaultEnvironments.add("staging");
        defaultEnvironments.add("integrationqa");
        defaultEnvironments.add("integrationdev");
        defaultEnvironments.add("development");
        
        blcPropertyLocations.add(new ClassPathResource("config/bc/"));
        blcPropertyLocations.add(new ClassPathResource("config/bc/admin/"));
        blcPropertyLocations.add(new ClassPathResource("config/bc/cms/"));
        blcPropertyLocations.add(new ClassPathResource("config/bc/web/"));
        blcPropertyLocations.add(new ClassPathResource("config/bc/fw/"));
        
        defaultPropertyLocations.add(new ClassPathResource("runtime-properties/"));
    }

    protected String defaultEnvironment = "development";
    protected String determinedEnvironment = null;
    protected RuntimeEnvironmentKeyResolver keyResolver;
    protected Set<String> environments = Collections.emptySet();
    protected Set<Resource> propertyLocations;
    protected Set<Resource> overridableProperyLocations;
    protected StringValueResolver stringValueResolver;

    public RuntimeEnvironmentPropertiesConfigurer() {
        super();
        setFileEncoding("utf-8");
        setIgnoreUnresolvablePlaceholders(true); // This default will get overriden by user options if present
        setNullValue("@null");
    }

    public void afterPropertiesSet() throws IOException {
        // If no environment override has been specified, used the default environments
        if (environments == null || environments.size() == 0) {
            environments = defaultEnvironments;
        }
        
        // Prepend the default property locations to the specified property locations (if any)
        Set<Resource> combinedLocations = new LinkedHashSet<Resource>();
        if (!CollectionUtils.isEmpty(overridableProperyLocations)) {
            combinedLocations.addAll(overridableProperyLocations);
        }

        if (!CollectionUtils.isEmpty(propertyLocations)) {
            combinedLocations.addAll(propertyLocations);
        }
        propertyLocations = combinedLocations;
    
        if (!environments.contains(defaultEnvironment)) {
            throw new AssertionError("Default environment '" + defaultEnvironment + "' not listed in environment list");
        }

        if (keyResolver == null) {
            keyResolver = new SystemPropertyRuntimeEnvironmentKeyResolver();
        }

        String environment = determineEnvironment();
        ArrayList<Resource> allLocations = new ArrayList<Resource>();
        
        /* Process configuration in the following order (later files override earlier files
         * common-shared.properties
         * [environment]-shared.properties
         * common.properties
         * [environment].properties
         * -Dproperty-override-shared specified value, if any
         * -Dproperty-override specified value, if any  */
        Set<Set<Resource>> testLocations = new LinkedHashSet<Set<Resource>>();
        testLocations.add(propertyLocations);
        testLocations.add(defaultPropertyLocations);

        for (Resource resource : createWakaResource()) {
            if (resource.exists()) {
                allLocations.add(resource);
            }
        }

        for (Set<Resource> locations : testLocations) {
            for (Resource resource : createSharedCommonResource(locations)) {
                if (resource.exists()) {
                    allLocations.add(resource);
                }
            }

            for (Resource resource : createSharedPropertiesResource(environment, locations)) {
                if (resource.exists()) {
                    allLocations.add(resource);
                }
            }

            for (Resource resource : createCommonResource(locations)) {
                if (resource.exists()) {
                    allLocations.add(resource);
                }
            }

            for (Resource resource : createPropertiesResource(environment, locations)) {
                if (resource.exists()) {
                    allLocations.add(resource);
                }
            }
        }

        Resource sharedPropertyOverride = createSharedOverrideResource();
        if (sharedPropertyOverride != null) {
            allLocations.add(sharedPropertyOverride);
        }
        
        Resource propertyOverride = createOverrideResource();
        if (propertyOverride != null) {
            allLocations.add(propertyOverride);
        }
        
        Properties props = new Properties();
        for (Resource resource : allLocations) {
            if (resource.exists()) {
                // We will log source-control managed properties with trace and overrides with info
                if (((resource.equals(sharedPropertyOverride) || resource.equals(propertyOverride)))
                        || LOG.isTraceEnabled()) {
                    props = new Properties(props);
                    props.load(resource.getInputStream());
                    for (Entry<Object, Object> entry : props.entrySet()) {
                        if (resource.equals(sharedPropertyOverride) || resource.equals(propertyOverride)) {
                            logger.support("Read " + entry.getKey() + " from " + resource.getFilename());
                        } else {
                            LOG.trace("Read " + entry.getKey() + " from " + resource.getFilename());
                        }
                    }
                }
            } else {
                LOG.debug("Unable to locate resource: " + resource.getFilename());
            }
        }

        setLocations(allLocations.toArray(new Resource[] {}));
    }
    
    protected Resource[] createSharedPropertiesResource(String environment, Set<Resource> locations) throws IOException {
        String fileName = environment.toString().toLowerCase() + "-shared.properties";
        Resource[] resources = new Resource[locations.size()];
        int index = 0;
        for (Resource resource : locations) {
            resources[index] = resource.createRelative(fileName);
            index++;
        }
        return resources;
    }
    
    protected Resource[] createWakaResource() throws IOException {
        Resource[] resources = new Resource[blcPropertyLocations.size()];
        int index = 0;
        for (Resource resource : blcPropertyLocations) {
            resources[index] = resource.createRelative("common.properties");
            index++;
        }
        return resources;
    }

    protected Resource[] createSharedCommonResource(Set<Resource> locations) throws IOException {
        Resource[] resources = new Resource[locations.size()];
        int index = 0;
        for (Resource resource : locations) {
            resources[index] = resource.createRelative("common-shared.properties");
            index++;
        }
        return resources;
    }

    protected Resource[] createPropertiesResource(String environment, Set<Resource> locations) throws IOException {
        String fileName = environment.toString().toLowerCase() + ".properties";
        Resource[] resources = new Resource[locations.size()];
        int index = 0;
        for (Resource resource : locations) {
            resources[index] = resource.createRelative(fileName);
            index++;
        }
        return resources;
    }

    protected Resource[] createCommonResource(Set<Resource> locations) throws IOException {
        Resource[] resources = new Resource[locations.size()];
        int index = 0;
        for (Resource resource : locations) {
            resources[index] = resource.createRelative("common.properties");
            index++;
        }
        return resources;
    }
    
    protected Resource createSharedOverrideResource() throws IOException {
        String path = System.getProperty(SHARED_PROPERTY_OVERRIDE);
        return StringUtils.isBlank(path) ? null : new FileSystemResource(path);
    }
    
    protected Resource createOverrideResource() throws IOException {
        String path = System.getProperty(PROPERTY_OVERRIDE);
        return StringUtils.isBlank(path) ? null : new FileSystemResource(path);
    }

    public String determineEnvironment() {
        if (determinedEnvironment != null) {
            return determinedEnvironment;
        }
        determinedEnvironment = keyResolver.resolveRuntimeEnvironmentKey();

        if (determinedEnvironment == null) {
            LOG.warn("Unable to determine runtime environment, using default environment '" + defaultEnvironment + "'");
            determinedEnvironment = defaultEnvironment;
        }

        return determinedEnvironment.toLowerCase();
    }

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        stringValueResolver = new PlaceholderResolvingStringValueResolver(props);
    }

    public void setDefaultEnvironment(String defaultEnvironment) {
        this.defaultEnvironment = defaultEnvironment;
    }

    public String getDefaultEnvironment() {
        return defaultEnvironment;
    }

    public void setKeyResolver(RuntimeEnvironmentKeyResolver keyResolver) {
        this.keyResolver = keyResolver;
    }

    public void setEnvironments(Set<String> environments) {
        this.environments = environments;
    }

    public void setPropertyLocations(Set<Resource> propertyLocations) {
        this.propertyLocations = propertyLocations;
    }

    public void setOverridableProperyLocations(Set<Resource> overridableProperyLocations) {
        this.overridableProperyLocations = overridableProperyLocations;
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final PropertyPlaceholderHelper helper;

        private final PropertyPlaceholderHelper.PlaceholderResolver resolver;

        public PlaceholderResolvingStringValueResolver(Properties props) {
            this.helper = new PropertyPlaceholderHelper("${", "}", ":", true);
            this.resolver = new PropertyPlaceholderConfigurerResolver(props);
        }

        public String resolveStringValue(String strVal) throws BeansException {
            String value = this.helper.replacePlaceholders(strVal, this.resolver);
            return (value.equals("") ? null : value);
        }
    }

    private class PropertyPlaceholderConfigurerResolver implements PropertyPlaceholderHelper.PlaceholderResolver {

        private final Properties props;

        private PropertyPlaceholderConfigurerResolver(Properties props) {
            this.props = props;
        }

        public String resolvePlaceholder(String placeholderName) {
            return RuntimeEnvironmentPropertiesConfigurer.this.resolvePlaceholder(placeholderName, props, 1);
        }
    }

    public StringValueResolver getStringValueResolver() {
        return stringValueResolver;
    }
}

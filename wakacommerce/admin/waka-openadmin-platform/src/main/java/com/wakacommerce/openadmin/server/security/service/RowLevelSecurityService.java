  
package com.wakacommerce.openadmin.server.security.service;

import java.util.List;

/**
 * <p>
 * Provides row-level security to the various CRUD operations in the admin
 * 
 * <p>
 * This security service can be extended by the use of {@link RowLevelSecurityProviders}, of which this service has a list.
 * To add additional providers, add this to an applicationContext merged into the admin application:
 * 
 * {@code
 *  <bean id="blCustomRowSecurityProviders" class="org.springframework.beans.factory.config.ListFactoryBean" >
 *       <property name="sourceList">
 *          <list>
 *              <ref bean="customProvider" />
 *          </list>
 *      </property>
 *  </bean>
 *  <bean class="com.wakacommerce.common.extensibility.context.merge.LateStageMergeBeanPostProcessor">
 *      <property name="collectionRef" value="blCustomRowSecurityProviders" />
 *      <property name="targetRef" value="blRowLevelSecurityProviders" />
 *  </bean>
 * }
 * 
 *     
 *   ( )
 */
public interface RowLevelSecurityService extends RowLevelSecurityProvider {

    /**
     * Gets all of the registered providers
     * @return the providers configured for this service
     */
    public List<RowLevelSecurityProvider> getProviders();
}

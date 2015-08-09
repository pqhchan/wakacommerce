
package com.wakacommerce.openadmin.server.service.persistence;

import org.springframework.core.Ordered;

import com.wakacommerce.common.exception.ServiceException;
import com.wakacommerce.openadmin.dto.CriteriaTransferObject;
import com.wakacommerce.openadmin.dto.DynamicResultSet;
import com.wakacommerce.openadmin.dto.Entity;
import com.wakacommerce.openadmin.dto.PersistencePackage;

/**
 * Interface for handling various lifecycle event for the {@link com.wakacommerce.openadmin.server.service.persistence.PersistenceManager}.
 * These events occur as part of the standard admin persistence lifecycle for entities.
 * <p/>
 * PersistenceManagerEventHandler instances are generally registered via the following approach in application
 * context xml
 * <p/>
 * {@code
 * <bean class="com.wakacommerce.common.extensibility.context.merge.LateStageMergeBeanPostProcessor">
 *      <property name="collectionRef" value="blSandBoxPersistenceManagerEventHandlers"/>
 *      <property name="targetRef" value="blPersistenceManagerEventHandlers"/>
 * </bean>
 * <bean id="blSandBoxPersistenceManagerEventHandlers" class="org.springframework.beans.factory.config.ListFactoryBean">
 *      <property name="sourceList">
 *          <list>
 *              <ref bean="blSandBoxPersistenceManagerEventHandler"/>
 *          </list>
*       </property>
 * </bean>
 * }
 *
 * 
 */
public interface PersistenceManagerEventHandler extends Ordered {

    PersistenceManagerEventHandlerResponse preInspect(PersistenceManager persistenceManager, PersistencePackage persistencePackage) throws ServiceException;

    PersistenceManagerEventHandlerResponse postInspect(PersistenceManager persistenceManager, DynamicResultSet resultSet, PersistencePackage persistencePackage) throws ServiceException;

    PersistenceManagerEventHandlerResponse preFetch(PersistenceManager persistenceManager, PersistencePackage persistencePackage, CriteriaTransferObject cto) throws ServiceException;

    PersistenceManagerEventHandlerResponse postFetch(PersistenceManager persistenceManager, DynamicResultSet resultSet, PersistencePackage persistencePackage, CriteriaTransferObject cto) throws ServiceException;

    PersistenceManagerEventHandlerResponse preAdd(PersistenceManager persistenceManager, PersistencePackage persistencePackage) throws ServiceException;

    PersistenceManagerEventHandlerResponse postAdd(PersistenceManager persistenceManager, Entity entity, PersistencePackage persistencePackage) throws ServiceException;

    PersistenceManagerEventHandlerResponse preUpdate(PersistenceManager persistenceManager, PersistencePackage persistencePackage) throws ServiceException;

    PersistenceManagerEventHandlerResponse postUpdate(PersistenceManager persistenceManager, Entity entity, PersistencePackage persistencePackage) throws ServiceException;

    PersistenceManagerEventHandlerResponse preRemove(PersistenceManager persistenceManager, PersistencePackage persistencePackage) throws ServiceException;

    PersistenceManagerEventHandlerResponse postRemove(PersistenceManager persistenceManager, PersistencePackage persistencePackage) throws ServiceException;

    PersistenceManagerEventHandlerResponse processValidationError(PersistenceManager persistenceManager, Entity entity, PersistencePackage persistencePackage) throws ServiceException;
}

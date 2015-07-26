
package com.wakacommerce.common.web;

/**
 * Defines the state in which sandboxable collections in the Enterprise module should adhere to Broadleaf defined behavior.
 * When FALSE, {@link org.hibernate.collection.spi.PersistentCollection} extensions in the Enterprise module will delegate
 * to the standard Hibernate behavior. This is useful when the desire is to build and persist entity object structures (that
 * the Enterprise module would otherwise interpret as sandboxable) without interference from the Enterprise module
 * on the collection persistence behavior. When the Enterprise module is loaded, the behavior is enforced by default.
 *
 *Jeff Fischer
 */
public enum EnforceEnterpriseCollectionBehaviorState {
    TRUE,FALSE,UNDEFINED
}

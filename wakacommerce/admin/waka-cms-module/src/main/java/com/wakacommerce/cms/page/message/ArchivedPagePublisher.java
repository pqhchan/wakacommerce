 
package com.wakacommerce.cms.page.message;

import com.wakacommerce.cms.page.domain.Page;

/**
 * The ArchivedPagePublisher will be notified when a page has
 * been marked as archived.    This provides a convenient cache-eviction
 * point for pages in production.
 *
 * Implementers of this service could send a JMS or AMQP message so
 * that other VMs can evict the item.
 *
 *   
 */
public interface ArchivedPagePublisher {
    void processPageArchive(Page page, String basePageKey);
}

 
package com.wakacommerce.cms.page.message;

import com.wakacommerce.cms.page.domain.Page;

/**
 *
 * @ hui
 */
public interface ArchivedPagePublisher {
    void processPageArchive(Page page, String basePageKey);
}

  
package com.wakacommerce.openadmin.web.service;

import com.wakacommerce.common.media.domain.Media;

/**
 *
 * @ hui
 */
public interface MediaBuilderService {

    public Media convertJsonToMedia(String json, Class<?> type);
}

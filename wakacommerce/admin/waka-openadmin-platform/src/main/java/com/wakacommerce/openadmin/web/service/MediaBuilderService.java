  
package com.wakacommerce.openadmin.web.service;

import com.wakacommerce.common.media.domain.Media;

/**
 *Chad Harchar (charchar)
 */
public interface MediaBuilderService {

    /**
     * Converts the given json {@link String} to {@link com.wakacommerce.common.media.domain.Media} given the
     * {@link java.lang.Class} that has been passed in.
     *
     * @param json the {@link String} to be converted to {@link Media}
     * @param type the {@link Class} that the {@link Media} should be
     */
    public Media convertJsonToMedia(String json, Class<?> type);
}

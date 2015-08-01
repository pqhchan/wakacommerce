  
package com.wakacommerce.openadmin.web.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wakacommerce.common.media.domain.Media;
import com.wakacommerce.common.media.domain.MediaDto;
import com.wakacommerce.common.persistence.EntityConfiguration;

import javax.annotation.Resource;

/**
 *Chad Harchar (charchar)
 */
@Service("blMediaBuilderService")
public class MediaBuilderServiceImpl implements MediaBuilderService {

    private static final Log LOG = LogFactory.getLog(MediaBuilderServiceImpl.class);

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    @Override
    public Media convertJsonToMedia(String json, Class<?> type) {
        if (json != null && !"".equals(json)) {
            try {
                ObjectMapper om = new ObjectMapper();
                om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                return (Media) om.readValue(json, type);
            } catch (Exception e) {
                LOG.warn("Error parsing json to media " + json, e);
            }
        }
        return entityConfiguration.createEntityInstance(MediaDto.class.getName(), MediaDto.class);
    }
}

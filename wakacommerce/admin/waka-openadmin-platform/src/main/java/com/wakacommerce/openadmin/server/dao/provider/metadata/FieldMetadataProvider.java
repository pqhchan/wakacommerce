package com.wakacommerce.openadmin.server.dao.provider.metadata;

import org.springframework.core.Ordered;

import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.AddMetadataFromFieldTypeRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.AddMetadataFromMappingDataRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.AddMetadataRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.LateStageAddMetadataRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.OverrideViaAnnotationRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.OverrideViaXmlRequest;
import com.wakacommerce.openadmin.server.service.type.FieldProviderResponse;

import java.util.Map;

/**
 * 实现类应该从{@link FieldMetadataProviderAdapter}扩展
 * 
 * @author hui
 */
public interface FieldMetadataProvider extends Ordered {

    public static final int BASIC = Integer.MAX_VALUE;
    public static final int COLLECTION = 20000;
    public static final int ADORNED_TARGET = 30000;
    public static final int MAP = 40000;
    public static final int MAP_FIELD = 50000;

    FieldProviderResponse addMetadata(AddMetadataRequest addMetadataRequest, Map<String, FieldMetadata> metadata);
    
    /**
     * Contribute to metadata inspection for the {@link java.lang.reflect.Field} instance in the request. Implementations should
     * add values to the metadata parameter.
     * 
     * This method differs from {@link #addMetadata(AddMetadataRequest, Map)} in that it will be invoked after the cacheable
     * properties are assembled. It is therefore useful in scenarios where you may want to contribute properties to 
     * metadata that are dynamic and should not be cached normally.
     *
     * @param lateStageAddMetadataRequest contains the requested field name and support classes.
     * @param metadata implementations should add metadata for the requested field here
     * @return whether or not this implementation adjusted metadata
     */
    FieldProviderResponse lateStageAddMetadata(LateStageAddMetadataRequest addMetadataRequest, Map<String, FieldMetadata> metadata);

    /**
     * Contribute to metadata inspection for the entity in the request. Implementations should override values
     * in the metadata parameter.
     *
     * @param overrideViaAnnotationRequest contains the requested entity and support classes.
     * @param metadata implementations should override metadata here
     * @return whether or not this implementation adjusted metadata
     */
    FieldProviderResponse overrideViaAnnotation(OverrideViaAnnotationRequest overrideViaAnnotationRequest, Map<String, FieldMetadata> metadata);

    /**
     * Contribute to metadata inspection for the ceiling entity and config key. Implementations should override
     * values in the metadata parameter.
     *
     * @param overrideViaXmlRequest contains the requested config key, ceiling entity and support classes.
     * @param metadata implementations should override metadata here
     * @return whether or not this implementation adjusted metadata
     */
    FieldProviderResponse overrideViaXml(OverrideViaXmlRequest overrideViaXmlRequest, Map<String, FieldMetadata> metadata);

    /**
     * 使用Hibernate的Column注解的信息来填充metadata
     */
    FieldProviderResponse addMetadataFromMappingData(AddMetadataFromMappingDataRequest addMetadataFromMappingDataRequest, FieldMetadata metadata);

    /**
     * Contribute to metadata inspection for the {@link java.lang.reflect.Field} instance in the request. Implementations should
     * add values to the metadata parameter. This is metadata based on the field type.
     */
    FieldProviderResponse addMetadataFromFieldType(AddMetadataFromFieldTypeRequest addMetadataFromFieldTypeRequest, Map<String, FieldMetadata> metadata);

}

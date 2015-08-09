
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
 *
 * @ hui
 */
public interface FieldMetadataProvider extends Ordered {

    //standard ordering constants for BLC providers
    public static final int BASIC = Integer.MAX_VALUE;
    public static final int COLLECTION = 20000;
    public static final int ADORNED_TARGET = 30000;
    public static final int MAP = 40000;
    public static final int MAP_FIELD = 50000;

    FieldProviderResponse addMetadata(AddMetadataRequest addMetadataRequest, Map<String, FieldMetadata> metadata);

    FieldProviderResponse lateStageAddMetadata(LateStageAddMetadataRequest addMetadataRequest, Map<String, FieldMetadata> metadata);

    FieldProviderResponse overrideViaAnnotation(OverrideViaAnnotationRequest overrideViaAnnotationRequest, Map<String, FieldMetadata> metadata);

    FieldProviderResponse overrideViaXml(OverrideViaXmlRequest overrideViaXmlRequest, Map<String, FieldMetadata> metadata);

    FieldProviderResponse addMetadataFromMappingData(AddMetadataFromMappingDataRequest addMetadataFromMappingDataRequest, FieldMetadata metadata);

    FieldProviderResponse addMetadataFromFieldType(AddMetadataFromFieldTypeRequest addMetadataFromFieldTypeRequest, Map<String, FieldMetadata> metadata);

}

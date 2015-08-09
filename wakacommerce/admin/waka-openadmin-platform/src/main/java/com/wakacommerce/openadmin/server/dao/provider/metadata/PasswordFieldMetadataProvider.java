package com.wakacommerce.openadmin.server.dao.provider.metadata;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.AddMetadataFromFieldTypeRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.AddMetadataFromMappingDataRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.AddMetadataRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.LateStageAddMetadataRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.OverrideViaAnnotationRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.OverrideViaXmlRequest;
import com.wakacommerce.openadmin.server.service.type.FieldProviderResponse;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
@Component("blPasswordFieldMetadataProvider")
@Scope("prototype")
public class PasswordFieldMetadataProvider extends AbstractFieldMetadataProvider implements FieldMetadataProvider {

    @Override
    public int getOrder() {
        return FieldMetadataProvider.BASIC;
    }

    @Override
    public FieldProviderResponse addMetadataFromFieldType(AddMetadataFromFieldTypeRequest addMetadataFromFieldTypeRequest, Map<String, FieldMetadata> metadata) {
        if (addMetadataFromFieldTypeRequest.getPresentationAttribute() instanceof BasicFieldMetadata && 
                SupportedFieldType.PASSWORD.equals(((BasicFieldMetadata) addMetadataFromFieldTypeRequest.getPresentationAttribute()).getExplicitFieldType())) {
            //build the metadata for the password field
            addMetadataFromFieldTypeRequest.getDynamicEntityDao().getDefaultFieldMetadataProvider().addMetadataFromFieldType(addMetadataFromFieldTypeRequest, metadata);
            ((BasicFieldMetadata)addMetadataFromFieldTypeRequest.getPresentationAttribute()).setFieldType(SupportedFieldType.PASSWORD);
            
            //clone the password field and add in a custom one
            BasicFieldMetadata confirmMd = (BasicFieldMetadata) addMetadataFromFieldTypeRequest.getPresentationAttribute().cloneFieldMetadata();
            confirmMd.setFieldName("passwordConfirm");
            confirmMd.setFriendlyName("确认密码");
            confirmMd.setExplicitFieldType(SupportedFieldType.PASSWORD_CONFIRM);
            confirmMd.setValidationConfigurations(new HashMap<String, Map<String,String>>());
            metadata.put("passwordConfirm", confirmMd);
            return FieldProviderResponse.HANDLED;
        } else {
            return FieldProviderResponse.NOT_HANDLED;
        }
    }
    
    @Override
    public FieldProviderResponse addMetadata(AddMetadataRequest addMetadataRequest, Map<String, FieldMetadata> metadata) {
        return FieldProviderResponse.NOT_HANDLED;
    }

    @Override
    public FieldProviderResponse lateStageAddMetadata(LateStageAddMetadataRequest addMetadataRequest, Map<String, FieldMetadata> metadata) {
        return FieldProviderResponse.NOT_HANDLED;
    }

    @Override
    public FieldProviderResponse overrideViaAnnotation(OverrideViaAnnotationRequest overrideViaAnnotationRequest, Map<String, FieldMetadata> metadata) {
        return FieldProviderResponse.NOT_HANDLED;
    }

    @Override
    public FieldProviderResponse overrideViaXml(OverrideViaXmlRequest overrideViaXmlRequest, Map<String, FieldMetadata> metadata) {
        return FieldProviderResponse.NOT_HANDLED;
    }

    @Override
    public FieldProviderResponse addMetadataFromMappingData(AddMetadataFromMappingDataRequest addMetadataFromMappingDataRequest, FieldMetadata metadata) {
        return FieldProviderResponse.NOT_HANDLED;
    }

}

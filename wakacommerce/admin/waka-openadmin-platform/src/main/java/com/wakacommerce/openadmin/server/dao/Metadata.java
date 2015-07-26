
package com.wakacommerce.openadmin.server.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.mapping.Property;
import org.hibernate.type.Type;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.presentation.AdminPresentationClass;
import com.wakacommerce.common.presentation.PopulateToOneFieldsEnum;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.MergedPropertyType;
import com.wakacommerce.openadmin.server.dao.provider.metadata.DefaultFieldMetadataProvider;
import com.wakacommerce.openadmin.server.dao.provider.metadata.FieldMetadataProvider;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.AddMetadataFromMappingDataRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.AddMetadataRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.OverrideViaAnnotationRequest;
import com.wakacommerce.openadmin.server.dao.provider.metadata.request.OverrideViaXmlRequest;
import com.wakacommerce.openadmin.server.service.type.FieldProviderResponse;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *Jeff Fischer
 */
@Component("blMetadata")
@Scope("prototype")
public class Metadata {

    private static final Log LOG = LogFactory.getLog(Metadata.class);

    @Resource(name="blMetadataProviders")
    protected List<FieldMetadataProvider> fieldMetadataProviders = new ArrayList<FieldMetadataProvider>();

    @Resource(name= "blDefaultFieldMetadataProvider")
    protected FieldMetadataProvider defaultFieldMetadataProvider;

    public Map<String, FieldMetadata> getFieldPresentationAttributes(Class<?> parentClass, Class<?> targetClass, DynamicEntityDao dynamicEntityDao, String prefix) {
        Map<String, FieldMetadata> attributes = new HashMap<String, FieldMetadata>();
        Field[] fields = dynamicEntityDao.getAllFields(targetClass);
        for (Field field : fields) {
            boolean foundOneOrMoreHandlers = false;
            for (FieldMetadataProvider fieldMetadataProvider : fieldMetadataProviders) {
                FieldProviderResponse response = fieldMetadataProvider.addMetadata(new AddMetadataRequest(field, parentClass, targetClass,
                        dynamicEntityDao, prefix), attributes);
                if (FieldProviderResponse.NOT_HANDLED != response) {
                    foundOneOrMoreHandlers = true;
                }
                if (FieldProviderResponse.HANDLED_BREAK == response) {
                    break;
                }
            }
            if (!foundOneOrMoreHandlers) {
                defaultFieldMetadataProvider.addMetadata(new AddMetadataRequest(field, parentClass, targetClass,
                        dynamicEntityDao, prefix), attributes);
            }
        }
        return attributes;
    }

    public Map<String, FieldMetadata> overrideMetadata(Class<?>[] entities, PropertyBuilder propertyBuilder, String prefix, Boolean isParentExcluded, String ceilingEntityFullyQualifiedClassname, String configurationKey, DynamicEntityDao dynamicEntityDao) {
        Boolean classAnnotatedPopulateManyToOneFields = null;
        //go in reverse order since I want the lowest subclass override to come last to guarantee that it takes effect
        for (int i = entities.length-1;i >= 0; i--) {
            AdminPresentationClass adminPresentationClass = entities[i].getAnnotation(AdminPresentationClass.class);
            if (adminPresentationClass != null && adminPresentationClass.populateToOneFields() != PopulateToOneFieldsEnum.NOT_SPECIFIED) {
                classAnnotatedPopulateManyToOneFields = adminPresentationClass.populateToOneFields()==PopulateToOneFieldsEnum.TRUE;
                break;
            }
        }

        Map<String, FieldMetadata> mergedProperties = propertyBuilder.execute(classAnnotatedPopulateManyToOneFields);
        for (int i = entities.length-1;i >= 0; i--) {
            boolean handled = false;
            for (FieldMetadataProvider fieldMetadataProvider : fieldMetadataProviders) {
                FieldProviderResponse response = fieldMetadataProvider.overrideViaAnnotation(new OverrideViaAnnotationRequest(entities[i],
                            isParentExcluded, dynamicEntityDao, prefix), mergedProperties);
                if (FieldProviderResponse.NOT_HANDLED != response) {
                    handled = true;
                }
                if (FieldProviderResponse.HANDLED_BREAK == response) {
                    break;
                }
            }
            if (!handled) {
                defaultFieldMetadataProvider.overrideViaAnnotation(new OverrideViaAnnotationRequest(entities[i],
                                         isParentExcluded, dynamicEntityDao, prefix), mergedProperties);
            }
        }
        ((DefaultFieldMetadataProvider) defaultFieldMetadataProvider).overrideExclusionsFromXml(new OverrideViaXmlRequest(configurationKey,
                ceilingEntityFullyQualifiedClassname, prefix, isParentExcluded, dynamicEntityDao), mergedProperties);

        boolean handled = false;
        for (FieldMetadataProvider fieldMetadataProvider : fieldMetadataProviders) {
            FieldProviderResponse response = fieldMetadataProvider.overrideViaXml(
                    new OverrideViaXmlRequest(configurationKey, ceilingEntityFullyQualifiedClassname, prefix,
                            isParentExcluded, dynamicEntityDao), mergedProperties);
            if (FieldProviderResponse.NOT_HANDLED != response) {
                handled = true;
            }
            if (FieldProviderResponse.HANDLED_BREAK == response) {
                break;
            }
        }
        if (!handled) {
            defaultFieldMetadataProvider.overrideViaXml(
                                new OverrideViaXmlRequest(configurationKey, ceilingEntityFullyQualifiedClassname, prefix,
                                        isParentExcluded, dynamicEntityDao), mergedProperties);
        }

        return mergedProperties;
    }

    public FieldMetadata getFieldMetadata(
        String prefix,
        String propertyName,
        List<Property> componentProperties,
        SupportedFieldType type,
        Type entityType,
        Class<?> targetClass,
        FieldMetadata presentationAttribute,
        MergedPropertyType mergedPropertyType,
        DynamicEntityDao dynamicEntityDao
    ) {
        return getFieldMetadata(prefix, propertyName, componentProperties, type, null, entityType, targetClass, presentationAttribute, mergedPropertyType, dynamicEntityDao);
    }

    public FieldMetadata getFieldMetadata(
        String prefix,
        final String propertyName,
        final List<Property> componentProperties,
        final SupportedFieldType type,
        final SupportedFieldType secondaryType,
        final Type entityType,
        Class<?> targetClass,
        final FieldMetadata presentationAttribute,
        final MergedPropertyType mergedPropertyType,
        final DynamicEntityDao dynamicEntityDao
    ) {
        if (presentationAttribute.getTargetClass() == null) {
            presentationAttribute.setTargetClass(targetClass.getName());
            presentationAttribute.setFieldName(propertyName);
        }
        presentationAttribute.setInheritedFromType(targetClass.getName());
        presentationAttribute.setAvailableToTypes(new String[]{targetClass.getName()});
        boolean handled = false;
        for (FieldMetadataProvider fieldMetadataProvider : fieldMetadataProviders) {
            FieldProviderResponse response = fieldMetadataProvider.addMetadataFromMappingData(new AddMetadataFromMappingDataRequest(
                componentProperties, type, secondaryType, entityType, propertyName, mergedPropertyType, dynamicEntityDao), presentationAttribute);
            if (FieldProviderResponse.NOT_HANDLED != response) {
                handled = true;
            }
            if (FieldProviderResponse.HANDLED_BREAK == response) {
                break;
            }
        }
        if (!handled) {
            defaultFieldMetadataProvider.addMetadataFromMappingData(new AddMetadataFromMappingDataRequest(
                    componentProperties, type, secondaryType, entityType, propertyName, mergedPropertyType, dynamicEntityDao), presentationAttribute);
        }

        return presentationAttribute;
    }

    public FieldMetadataProvider getDefaultFieldMetadataProvider() {
        return defaultFieldMetadataProvider;
    }

    public void setDefaultFieldMetadataProvider(FieldMetadataProvider defaultFieldMetadataProvider) {
        this.defaultFieldMetadataProvider = defaultFieldMetadataProvider;
    }

    public List<FieldMetadataProvider> getFieldMetadataProviders() {
        return fieldMetadataProviders;
    }

    public void setFieldMetadataProviders(List<FieldMetadataProvider> fieldMetadataProviders) {
        this.fieldMetadataProviders = fieldMetadataProviders;
    }
}

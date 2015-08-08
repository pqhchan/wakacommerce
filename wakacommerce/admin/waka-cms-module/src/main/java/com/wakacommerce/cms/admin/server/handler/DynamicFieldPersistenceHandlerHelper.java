package com.wakacommerce.cms.admin.server.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import com.wakacommerce.cms.field.domain.FieldDefinition;
import com.wakacommerce.cms.field.domain.FieldGroup;
import com.wakacommerce.common.enumeration.domain.DataDrivenEnumerationValue;
import com.wakacommerce.common.presentation.ConfigurationItem;
import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.presentation.client.VisibilityEnum;
import com.wakacommerce.openadmin.dto.BasicFieldMetadata;
import com.wakacommerce.openadmin.dto.MergedPropertyType;
import com.wakacommerce.openadmin.dto.Property;


@Component("blDynamicFieldPersistenceHandlerHelper")
public class DynamicFieldPersistenceHandlerHelper {
    
    public Property buildDynamicProperty(FieldDefinition definition, Class<?> inheritedType) {
        Property property = new Property();
        property.setName(definition.getName());
        BasicFieldMetadata fieldMetadata = new BasicFieldMetadata();
        property.setMetadata(fieldMetadata);
        fieldMetadata.setFieldType(definition.getFieldType());
        
        fieldMetadata.setMutable(true);
        fieldMetadata.setInheritedFromType(inheritedType.getName());
        fieldMetadata.setAvailableToTypes(new String[] {inheritedType.getName()});
        fieldMetadata.setForeignKeyCollection(false);
        fieldMetadata.setMergedPropertyType(MergedPropertyType.PRIMARY);
        fieldMetadata.setLength(definition.getMaxLength());
        if (definition.getDataDrivenEnumeration() != null && !CollectionUtils.isEmpty(definition.getDataDrivenEnumeration().getEnumValues())) {
            int count = definition.getDataDrivenEnumeration().getEnumValues().size();
            String[][] enumItems = new String[count][2];
            for (int j = 0; j < count; j++) {
                DataDrivenEnumerationValue item = definition.getDataDrivenEnumeration().getEnumValues().get(j);
                enumItems[j][0] = item.getKey();
                enumItems[j][1] = item.getDisplay();
            }
            fieldMetadata.setEnumerationValues(enumItems);
        }
        fieldMetadata.setName(definition.getName());
        fieldMetadata.setFriendlyName(definition.getFriendlyName());
        fieldMetadata.setVisibility(definition.getHiddenFlag()?VisibilityEnum.HIDDEN_ALL:VisibilityEnum.VISIBLE_ALL);
        fieldMetadata.setTab("General");
        fieldMetadata.setTabOrder(100);
        fieldMetadata.setExplicitFieldType(SupportedFieldType.UNKNOWN);
        fieldMetadata.setLargeEntry(definition.getTextAreaFlag());
        fieldMetadata.setProminent(false);
        fieldMetadata.setColumnWidth(String.valueOf(definition.getColumnWidth()));
        fieldMetadata.setWakaEnumType("");
        fieldMetadata.setReadOnly(false);
        fieldMetadata.setRequiredOverride(definition.getRequiredFlag());
        fieldMetadata.setHint(definition.getHint());
        fieldMetadata.setHelpText(definition.getHelpText());
        fieldMetadata.setTooltip(definition.getTooltip());
        if (definition.getValidationRegEx() != null) {
            Map<String, String> itemMap = new HashMap<String, String>();
            itemMap.put("regularExpression", definition.getValidationRegEx());
            itemMap.put(ConfigurationItem.ERROR_MESSAGE, definition.getValidationErrorMesageKey());
            fieldMetadata.getValidationConfigurations().put("com.wakacommerce.openadmin.server.service.persistence.validation.RegexPropertyValidator", itemMap);
        }
        
        
        if (definition.getFieldType().equals(SupportedFieldType.ADDITIONAL_FOREIGN_KEY)) {
            fieldMetadata.setForeignKeyClass(definition.getAdditionalForeignKeyClass());
            fieldMetadata.setOwningClass(definition.getAdditionalForeignKeyClass());
            fieldMetadata.setForeignKeyDisplayValueProperty("__adminMainEntity");
        }

        return property;
    }

    public Property[] buildDynamicPropertyList(List<FieldGroup> fieldGroups, Class<?> inheritedType) {
        List<Property> propertiesList = new ArrayList<Property>();
        int groupCount = 1;
        int fieldCount = 0;
        for (FieldGroup group : fieldGroups) {
            List<FieldDefinition> definitions = group.getFieldDefinitions();
            for (FieldDefinition def : definitions) {
                Property property = buildDynamicProperty(def, inheritedType);
                BasicFieldMetadata fieldMetadata = (BasicFieldMetadata) property.getMetadata();
                fieldMetadata.setOrder(fieldCount++);
                fieldMetadata.setGroup(group.getName());
                fieldMetadata.setGroupOrder(groupCount);
                fieldMetadata.setGroupCollapsed(group.getInitCollapsedFlag());
                propertiesList.add(property);
            }
            groupCount++;
            fieldCount = 0;
        }
        Property property = new Property();
        property.setName("id");
        BasicFieldMetadata fieldMetadata = new BasicFieldMetadata();
        property.setMetadata(fieldMetadata);
        fieldMetadata.setFieldType(SupportedFieldType.ID);
        fieldMetadata.setSecondaryType(SupportedFieldType.INTEGER);
        fieldMetadata.setMutable(true);
        fieldMetadata.setInheritedFromType(inheritedType.getName());
        fieldMetadata.setAvailableToTypes(new String[] {inheritedType.getName()});
        fieldMetadata.setForeignKeyCollection(false);
        fieldMetadata.setMergedPropertyType(MergedPropertyType.PRIMARY);
        fieldMetadata.setName("id");
        fieldMetadata.setFriendlyName("ID");
        fieldMetadata.setSecurityLevel("");
        fieldMetadata.setVisibility(VisibilityEnum.HIDDEN_ALL);
        fieldMetadata.setExplicitFieldType(SupportedFieldType.UNKNOWN);
        fieldMetadata.setLargeEntry(false);
        fieldMetadata.setProminent(false);
        fieldMetadata.setColumnWidth("*");
        fieldMetadata.setWakaEnumType("");
        fieldMetadata.setReadOnly(true);
        propertiesList.add(property);

        Property[] properties = new Property[propertiesList.size()];
        properties = propertiesList.toArray(properties);
        Arrays.sort(properties, new Comparator<Property>() {
            @Override
            public int compare(Property o1, Property o2) {
                /*
                     * First, compare properties based on order fields
                     */
                if (o1.getMetadata().getOrder() != null && o2.getMetadata().getOrder() != null) {
                    return o1.getMetadata().getOrder().compareTo(o2.getMetadata().getOrder());
                } else if (o1.getMetadata().getOrder() != null && o2.getMetadata().getOrder() == null) {
                    /*
                          * Always favor fields that have an order identified
                          */
                    return -1;
                } else if (o1.getMetadata().getOrder() == null && o2.getMetadata().getOrder() != null) {
                    /*
                          * Always favor fields that have an order identified
                          */
                    return 1;
                } else if (o1.getMetadata().getFriendlyName() != null && o2.getMetadata().getFriendlyName() != null) {
                    return o1.getMetadata().getFriendlyName().compareTo(o2.getMetadata().getFriendlyName());
                } else {
                    return o1.getName().compareTo(o2.getName());
                }
            }
        });
        return properties;
    }

}

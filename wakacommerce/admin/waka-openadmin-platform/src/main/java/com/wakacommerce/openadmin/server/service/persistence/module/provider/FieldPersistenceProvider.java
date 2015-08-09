
package com.wakacommerce.openadmin.server.service.persistence.module.provider;

import org.springframework.core.Ordered;

import com.wakacommerce.openadmin.dto.FieldMetadata;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.module.criteria.FilterMapping;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.AddFilterPropertiesRequest;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.AddSearchMappingRequest;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest;
import com.wakacommerce.openadmin.server.service.type.FieldProviderResponse;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *
 * @ hui
 */
public interface FieldPersistenceProvider extends Ordered {

    //standard ordering constants for BLC providers
    public static final int BASIC = Integer.MAX_VALUE;

    public static final int MEDIA = 20000;

    public static final int RULE = 30000;
    public static final int MAP_FIELD = 40000;
    public static final int MONEY = 50000;

    FieldProviderResponse populateValue(PopulateValueRequest populateValueRequest, Serializable instance);

    FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property);

    FieldProviderResponse addSearchMapping(AddSearchMappingRequest addSearchMappingRequest, List<FilterMapping> filterMappings);

    FieldProviderResponse filterProperties(AddFilterPropertiesRequest addFilterPropertiesRequest, Map<String, FieldMetadata> properties);

    boolean alwaysRun();
}

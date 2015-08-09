  
package com.wakacommerce.openadmin.server.service.persistence.module.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.wakacommerce.common.presentation.client.SupportedFieldType;
import com.wakacommerce.common.web.WakaRequestContext;
import com.wakacommerce.openadmin.dto.Property;
import com.wakacommerce.openadmin.server.service.persistence.PersistenceException;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.ExtractValueRequest;
import com.wakacommerce.openadmin.server.service.persistence.module.provider.request.PopulateValueRequest;
import com.wakacommerce.openadmin.server.service.type.FieldProviderResponse;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @ hui
 */
@Component("blHTMLFieldPersistenceProvider")
@Scope("prototype")
public class HTMLFieldPersistenceProvider extends FieldPersistenceProviderAdapter {

    @Value("${asset.server.url.prefix.internal}")
    protected String staticAssetUrlPrefix;

    protected boolean canHandlePersistence(PopulateValueRequest populateValueRequest, Serializable instance) {
        return populateValueRequest.getMetadata().getFieldType() == SupportedFieldType.HTML ||
                populateValueRequest.getMetadata().getFieldType() == SupportedFieldType.HTML_BASIC;
    }

    protected boolean canHandleExtraction(ExtractValueRequest extractValueRequest, Property property) {
        return extractValueRequest.getMetadata().getFieldType() == SupportedFieldType.HTML ||
                extractValueRequest.getMetadata().getFieldType() == SupportedFieldType.HTML_BASIC;
    }

    @Override
    public FieldProviderResponse populateValue(PopulateValueRequest populateValueRequest, Serializable instance) throws PersistenceException {
        if (!canHandlePersistence(populateValueRequest, instance)) {
            return FieldProviderResponse.NOT_HANDLED;
        }

        try {
            String requestedValue = populateValueRequest.getRequestedValue();
            String fixedValue = fixAssetPathsForStorage(requestedValue);

            boolean dirty = checkDirtyState(populateValueRequest, instance, fixedValue);
            populateValueRequest.getProperty().setIsDirty(dirty);

            populateValueRequest.getFieldManager().setFieldValue(instance,
                    populateValueRequest.getProperty().getName(), fixedValue);

        } catch (Exception e) {
            throw new PersistenceException(e);
        }
        return FieldProviderResponse.HANDLED_BREAK;
    }

    @Override
    public FieldProviderResponse extractValue(ExtractValueRequest extractValueRequest, Property property) throws PersistenceException {
        if (!canHandleExtraction(extractValueRequest, property)) {
            return FieldProviderResponse.NOT_HANDLED;
        }

        if (extractValueRequest.getRequestedValue() != null) {
            String val = extractValueRequest.getRequestedValue().toString();
            if (val != null) {
                if (val.contains(staticAssetUrlPrefix)) {
                    val = fixAssetPathsForDisplay(val);
                }
            }
            property.setValue(val);
            property.setDisplayValue(extractValueRequest.getDisplayVal());
        }
        return FieldProviderResponse.HANDLED_BREAK;
    }

    public String fixAssetPathsForStorage(String val) {
        if (staticAssetUrlPrefix != null) {
            String tmpPrefix = staticAssetUrlPrefix;
            if (tmpPrefix.startsWith("/")) {
                tmpPrefix = tmpPrefix.substring(1);
            }
            return val.replaceAll("(?<=src=\").*?(?=" + tmpPrefix + ")", "");
        }
        return val;
    }

    public String fixAssetPathsForDisplay(String val) {
        String contextPath = "/";
        WakaRequestContext brc = WakaRequestContext.getWakaRequestContext();
        if (brc != null) {                        
            HttpServletRequest request = brc.getRequest();
            if (request != null) {
                contextPath = request.getContextPath();
            }
        }
        
        if (!contextPath.endsWith("/")) {
            contextPath = contextPath + "/";
        }
        
        if (staticAssetUrlPrefix != null) {
            String tmpPrefix = staticAssetUrlPrefix;
            if (tmpPrefix.startsWith("/")) {
                tmpPrefix = tmpPrefix.substring(1);
            }
            return val.replaceAll("(?<=src=\").*?(?=" + tmpPrefix + ")", contextPath);
        }
        
        return val;
    }

}

package com.wakacommerce.openadmin.server.service.artifact;

import java.io.InputStream;
import java.util.Map;

import com.wakacommerce.openadmin.server.service.artifact.image.Operation;

public interface OperationBuilder {

    public Operation buildOperation(Map<String, String> parameterMap, InputStream artifactStream, String mimeType);

}

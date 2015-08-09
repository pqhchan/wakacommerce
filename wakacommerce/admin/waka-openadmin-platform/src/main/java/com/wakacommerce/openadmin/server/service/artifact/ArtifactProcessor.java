package com.wakacommerce.openadmin.server.service.artifact;

import java.io.InputStream;
import java.util.Map;

import com.wakacommerce.openadmin.server.service.artifact.image.Operation;

/**
 *
 * @ hui
 */
public interface ArtifactProcessor {

    public boolean isSupported(InputStream artifactStream, String mimeType);

    public InputStream convert(InputStream artifactStream, Operation[] operations, String mimeType) throws Exception;

    public Operation[] buildOperations(Map<String, String> parameterMap, InputStream artifactStream, String mimeType);

}

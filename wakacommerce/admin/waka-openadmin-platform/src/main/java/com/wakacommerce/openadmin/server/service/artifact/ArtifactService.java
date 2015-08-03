package com.wakacommerce.openadmin.server.service.artifact;

import java.io.InputStream;
import java.util.Map;

import com.wakacommerce.openadmin.server.service.artifact.image.Operation;

public interface ArtifactService {

    InputStream convert(InputStream artifactStream, Operation[] operations, String mimeType) throws Exception;

    ArtifactProcessor[] getArtifactProcessors();

    void setArtifactProcessors(ArtifactProcessor[] artifactProcessors);

    public Operation[] buildOperations(Map<String, String> parameterMap, InputStream artifactStream, String mimeType);
}


package com.wakacommerce.openadmin.server.service.artifact;

import java.io.InputStream;
import java.util.Map;

import com.wakacommerce.openadmin.server.service.artifact.image.Operation;

/**
 * Created by IntelliJ IDEA.
 * User:   
 * Date: 9/10/11
 * Time: 3:33 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ArtifactService {

    InputStream convert(InputStream artifactStream, Operation[] operations, String mimeType) throws Exception;

    ArtifactProcessor[] getArtifactProcessors();

    void setArtifactProcessors(ArtifactProcessor[] artifactProcessors);

    public Operation[] buildOperations(Map<String, String> parameterMap, InputStream artifactStream, String mimeType);
}

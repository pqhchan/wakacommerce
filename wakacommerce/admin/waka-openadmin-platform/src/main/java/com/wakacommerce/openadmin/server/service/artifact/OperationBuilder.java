
package com.wakacommerce.openadmin.server.service.artifact;

import java.io.InputStream;
import java.util.Map;

import com.wakacommerce.openadmin.server.service.artifact.image.Operation;

/**
 * Created by IntelliJ IDEA.
 * User:   
 * Date: 9/10/11
 * Time: 6:45 PM
 * To change this template use File | Settings | File Templates.
 */
public interface OperationBuilder {

    public Operation buildOperation(Map<String, String> parameterMap, InputStream artifactStream, String mimeType);

}

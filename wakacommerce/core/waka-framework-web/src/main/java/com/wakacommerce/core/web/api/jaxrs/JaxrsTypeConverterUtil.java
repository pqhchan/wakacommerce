
package com.wakacommerce.core.web.api.jaxrs;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.core.UriInfo;

/**
 *
 * @ hui
 */
@Deprecated
public class JaxrsTypeConverterUtil {

    public static MultiValueMap<String, String> convertJaxRSUriInfoToParameterMap(UriInfo uriInfo) {
        LinkedMultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
        for (Entry<String, List<String>> entry : uriInfo.getQueryParameters().entrySet()) {
            paramMap.put(entry.getKey(), entry.getValue());
        }
        return paramMap;
    }

}

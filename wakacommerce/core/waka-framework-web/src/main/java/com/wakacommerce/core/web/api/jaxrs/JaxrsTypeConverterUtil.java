  
package com.wakacommerce.core.web.api.jaxrs;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.core.UriInfo;

/**
 * Utility to convert from JAXRS types into something Spring-MVC compatible
 *
 *     
 * @deprecated along with the other JAXRS components, this is deprecated in favor of using Spring MVC for REST services
 */
@Deprecated
public class JaxrsTypeConverterUtil {

    /**
     * Converts a the given JAXRS {@link UriInfo} into something that works for Spring MVC
     * 
     * @param uriInfo
     * @return
     */
    public static MultiValueMap<String, String> convertJaxRSUriInfoToParameterMap(UriInfo uriInfo) {
        LinkedMultiValueMap<String, String> paramMap = new LinkedMultiValueMap<String, String>();
        for (Entry<String, List<String>> entry : uriInfo.getQueryParameters().entrySet()) {
            paramMap.put(entry.getKey(), entry.getValue());
        }
        return paramMap;
    }

}

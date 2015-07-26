
package com.wakacommerce.openadmin.web.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.wakacommerce.common.web.BroadleafTimeZoneResolverImpl;

import java.util.TimeZone;


/**
 * 
 *Phillip Verheyden (phillipuniverse)
 */
@Component("blAdminTimeZoneResolver")
public class BroadleafAdminTimeZoneResolver extends BroadleafTimeZoneResolverImpl {

    @Override
    public TimeZone resolveTimeZone(WebRequest request) {
        //TODO: eventually this should support a using a timezone from the currently logged in Admin user preferences
        return super.resolveTimeZone(request);
    }
}

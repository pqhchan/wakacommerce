
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import java.util.TimeZone;

/**
 *
 * @ hui
 */
public interface BroadleafTimeZoneResolver  {


    public TimeZone resolveTimeZone(WebRequest request);

}

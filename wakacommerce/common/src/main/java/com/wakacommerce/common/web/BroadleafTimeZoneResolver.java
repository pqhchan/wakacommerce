
package com.wakacommerce.common.web;

import org.springframework.web.context.request.WebRequest;

import java.util.TimeZone;

/**
 * Responsible for returning the TimeZone to use for the current request.
 *
 *Priyesh Patel
 */
public interface BroadleafTimeZoneResolver  {


    public TimeZone resolveTimeZone(WebRequest request);

}

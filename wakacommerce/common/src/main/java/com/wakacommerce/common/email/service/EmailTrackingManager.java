
package com.wakacommerce.common.email.service;

import java.util.Map;


/**
 *jfischer
 *
 */
public interface EmailTrackingManager {

    public Long createTrackedEmail(String emailAddress, String type, String extraValue);
    public void recordOpen (Long emailId, Map<String, String> extraValues);
    public void recordClick(Long emailId , Map<String, String> parameterMap, String customerId, Map<String, String> extraValues);

}
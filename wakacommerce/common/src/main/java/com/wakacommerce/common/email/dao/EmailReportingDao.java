
package com.wakacommerce.common.email.dao;

import com.wakacommerce.common.email.domain.EmailTarget;
import com.wakacommerce.common.email.domain.EmailTracking;

/**
 *jfischer
 *
 */
public interface EmailReportingDao {

    public Long createTracking(String emailAddress, String type, String extraValue) ;
    public void recordOpen(Long emailId, String userAgent);
    public void recordClick(Long emailId, String customerId, String destinationUri, String queryString);
    public EmailTracking retrieveTracking(Long emailId);
    public EmailTarget createTarget();

}

package com.wakacommerce.common.email.dao;

import org.springframework.stereotype.Repository;

import com.wakacommerce.common.email.domain.EmailTarget;
import com.wakacommerce.common.email.domain.EmailTracking;
import com.wakacommerce.common.email.domain.EmailTrackingClicks;
import com.wakacommerce.common.email.domain.EmailTrackingOpens;
import com.wakacommerce.common.persistence.EntityConfiguration;
import com.wakacommerce.common.time.SystemTime;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository("blEmailReportingDao")
public class EmailReportingDaoImpl implements EmailReportingDao {

    @PersistenceContext(unitName="blPU")
    protected EntityManager em;

    @Resource(name="blEntityConfiguration")
    protected EntityConfiguration entityConfiguration;

    public Long createTracking(String emailAddress, String type, String extraValue) {
        EmailTracking tracking = (EmailTracking) entityConfiguration.createEntityInstance("com.wakacommerce.common.email.domain.EmailTracking");
        tracking.setDateSent(SystemTime.asDate());
        tracking.setEmailAddress(emailAddress);
        tracking.setType(type);

        em.persist(tracking);

        return tracking.getId();
    }

    public EmailTarget createTarget() {
        EmailTarget target = (EmailTarget) entityConfiguration.createEntityInstance("com.wakacommerce.common.email.domain.EmailTarget");
        return target;
    }

    public EmailTracking retrieveTracking(Long emailId) {
        return (EmailTracking) em.find(entityConfiguration.lookupEntityClass("com.wakacommerce.common.email.domain.EmailTracking"), emailId);
    }

    public void recordOpen(Long emailId, String userAgent) {
        EmailTrackingOpens opens = (EmailTrackingOpens) entityConfiguration.createEntityInstance("com.wakacommerce.common.email.domain.EmailTrackingOpens");
        opens.setEmailTracking(retrieveTracking(emailId));
        opens.setDateOpened(SystemTime.asDate());
        opens.setUserAgent(userAgent);

        em.persist(opens);
    }

    public void recordClick(Long emailId, String customerId, String destinationUri, String queryString) {
        EmailTrackingClicks clicks = (EmailTrackingClicks) entityConfiguration.createEntityInstance("com.wakacommerce.common.email.domain.EmailTrackingClicks");
        clicks.setEmailTracking(retrieveTracking(emailId));
        clicks.setDateClicked(SystemTime.asDate());
        clicks.setDestinationUri(destinationUri);
        clicks.setQueryString(queryString);
        clicks.setCustomerId(customerId);

        em.persist(clicks);
    }
}

package com.wakacommerce.common.email.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.common.email.dao.EmailReportingDao;
import com.wakacommerce.common.util.TransactionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;

@Service("blEmailTrackingManager")
public class EmailTrackingManagerImpl implements EmailTrackingManager {

    private static final Log LOG = LogFactory.getLog(EmailTrackingManagerImpl.class);

    @Resource(name = "blEmailReportingDao")
    protected EmailReportingDao emailReportingDao;

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public Long createTrackedEmail(String emailAddress, String type, String extraValue) {
        return emailReportingDao.createTracking(emailAddress, type, extraValue);
    }

    @Override
    @Transactional(TransactionUtils.DEFAULT_TRANSACTION_MANAGER)
    public void recordClick(Long emailId, Map<String, String> parameterMap, String customerId, Map<String, String> extraValues) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("recordClick() => Click detected for Email[" + emailId + "]");
        }

        Iterator<String> keys = parameterMap.keySet().iterator();
        // clean up and normalize the query string
        ArrayList<String> queryParms = new ArrayList<String>();
        while (keys.hasNext()) {
            String p = keys.next();
            // exclude email_id from the parms list
            if (!p.equals("email_id")) {
                queryParms.add(p);
            }
        }

        String newQuery = null;

        if (!queryParms.isEmpty()) {

            String[] p = queryParms.toArray(new String[queryParms.size()]);
            Arrays.sort(p);

            StringBuffer newQueryParms = new StringBuffer();
            for (int cnt = 0; cnt < p.length; cnt++) {
                newQueryParms.append(p[cnt]);
                newQueryParms.append("=");
                newQueryParms.append(parameterMap.get(p[cnt]));
                if (cnt != p.length - 1) {
                    newQueryParms.append("&");
                }
            }
            newQuery = newQueryParms.toString();
        }

        emailReportingDao.recordClick(emailId, customerId, extraValues.get("requestUri"), newQuery);
    }

    /*
     * (non-Javadoc)
     * @see
     * com.containerstore.web.task.service.EmailTrackingManager#recordOpen(java
     * .lang.String, javax.servlet.http.HttpServletRequest)
     */
    @Override
    public void recordOpen(Long emailId, Map<String, String> extraValues) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Recording open for email id: " + emailId);
        }
        // extract necessary information from the request and record the open
        emailReportingDao.recordOpen(emailId, extraValues.get("userAgent"));
    }

}

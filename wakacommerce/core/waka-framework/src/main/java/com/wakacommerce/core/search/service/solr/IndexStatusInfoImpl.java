
package com.wakacommerce.core.search.service.solr;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
public class IndexStatusInfoImpl implements IndexStatusInfo {

    private Date lastIndexDate;
    private Map<String, String> additionalInfo = new HashMap<String, String>();

    @Override
    public Date getLastIndexDate() {
        return lastIndexDate;
    }

    @Override
    public void setLastIndexDate(Date lastIndexDate) {
        this.lastIndexDate = lastIndexDate;
    }

    @Override
    public Map<String, String> getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    public void setAdditionalInfo(Map<String, String> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
}

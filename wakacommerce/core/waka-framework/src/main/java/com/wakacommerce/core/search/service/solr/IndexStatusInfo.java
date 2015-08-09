
package com.wakacommerce.core.search.service.solr;

import java.util.Date;
import java.util.Map;

/**
 *
 * @ hui
 */
public interface IndexStatusInfo {

    Date getLastIndexDate();

    void setLastIndexDate(Date lastIndexDate);

    Map<String, String> getAdditionalInfo();

    void setAdditionalInfo(Map<String, String> additionalInfo);

}

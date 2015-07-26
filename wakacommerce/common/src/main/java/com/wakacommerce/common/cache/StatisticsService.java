
package com.wakacommerce.common.cache;

/**
 *Jeff Fischer
 */
public interface StatisticsService {
    void addCacheStat(String key, boolean isHit);

    Long getLogResolution();

    void setLogResolution(Long logResolution);

    void activateLogging();

    void disableLogging();
}

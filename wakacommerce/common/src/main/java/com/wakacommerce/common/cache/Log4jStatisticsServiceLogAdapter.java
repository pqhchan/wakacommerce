
package com.wakacommerce.common.cache;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;

/**
 * Specific implementation used with a Log4j dependency
 *  
 */
public class Log4jStatisticsServiceLogAdapter implements StatisticsServiceLogAdapter {

    @Override
    public void activateLogging(Class clazz) {
        LogManager.getLogger(clazz).setLevel(Level.INFO);
    }

    @Override
    public void disableLogging(Class clazz) {
        LogManager.getLogger(StatisticsServiceImpl.class).setLevel(Level.DEBUG);
    }

}

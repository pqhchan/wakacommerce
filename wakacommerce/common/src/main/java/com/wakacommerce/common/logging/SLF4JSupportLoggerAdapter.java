
package com.wakacommerce.common.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

/**
 *
 * @ hui
 */
public class SLF4JSupportLoggerAdapter extends AbstractSupportLoggerAdapter implements SupportLoggerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(SLF4JSupportLoggerAdapter.class);
    public static final String DEFAULT_LEVEL_KEY = "SLF4JSupportLoggerAdapter.defaultLevel";

    private String name;

    @Override
    public void support(String message) {
        mapSupportLevel(message, null);
    }

    @Override
    public void support(String message, Throwable t) {
        mapSupportLevel(message, t);
    }

    @Override
    public void lifecycle(LifeCycleEvent lifeCycleEvent, String message) {
        mapSupportLevel(message, null);
    }

    @Override
    public void debug(String message) {
        LOGGER.debug(message);
    }

    @Override
    public void debug(String message, Throwable t) {
        LOGGER.debug(message, t);
    }

    @Override
    public void error(String message) {
        LOGGER.error(message);
    }

    @Override
    public void error(String message, Throwable t) {
        LOGGER.error(message, t);
    }

    @Override
    public void fatal(String message) {
        LOGGER.error(message);
    }

    @Override
    public void fatal(String message, Throwable t) {
        LOGGER.error(message, t);
    }

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void info(String message, Throwable t) {
        LOGGER.info(message, t);
    }

    @Override
    public void warn(String message) {
        LOGGER.warn(message);
    }

    @Override
    public void warn(String message, Throwable t) {
        LOGGER.warn(message, t);
    }

    protected void mapSupportLevel(String message, Throwable t) {
        Marker supportMarker = MarkerFactory.getMarker(SUPPORT);

        switch (getSupportLevel()) {
            case LOG_LEVEL_ERROR:
                LOGGER.error(supportMarker, message, t);
                break;
            case LOG_LEVEL_INFO:
                LOGGER.info(supportMarker, message, t);
                break;
            case LOG_LEVEL_DEBUG:
                LOGGER.debug(supportMarker, message, t);
                break;
            case LOG_LEVEL_TRACE:
                LOGGER.trace(supportMarker, message, t);
                break;
            default:
                LOGGER.warn(supportMarker, message, t);
        }

    }

    public int getSupportLevel() {
        String systemProperty = System.getProperty(DEFAULT_LEVEL_KEY, LOG_LEVEL_WARN+"");
        return Integer.valueOf(systemProperty);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}

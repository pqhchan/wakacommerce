
package com.wakacommerce.common.logging;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @ hui
 */
public class SystemSupportLoggerAdapter extends AbstractSupportLoggerAdapter implements SupportLoggerAdapter {

    public static final String SHOW_DATE_TIME_KEY = "SystemSupportLoggerAdapter.showDateTime";
    public static final String DATE_TIME_FORMAT_KEY = "SystemSupportLoggerAdapter.dateTimeFormat";
    public static final String SHOW_THREAD_NAME_KEY = "SystemSupportLoggerAdapter.showThreadName";
    public static final String SHOW_LOG_NAME_KEY = "SystemSupportLoggerAdapter.showLogName";
    public static final String LEVEL_IN_BRACKETS_KEY = "SystemSupportLoggerAdapter.levelInBrackets";
    public static final String SHOW_NON_SUPPORT_LEVELS_KEY = "SystemSupportLoggerAdapter.showNonSupportLevels";

    private String name;

    @Override
    public void support(String message) {
        log(LOG_LEVEL_SUPPORT, message, null);
    }

    @Override
    public void support(String message, Throwable t) {
        log(LOG_LEVEL_SUPPORT, message, t);
    }

    @Override
    public void lifecycle(LifeCycleEvent lifeCycleEvent, String message) {
        log(LOG_LEVEL_SUPPORT, message, null);
    }

    @Override
    public void debug(String message) {
        if (getShowNonSupportLevels()) {
            log(LOG_LEVEL_DEBUG, message, null);
        }
    }

    @Override
    public void debug(String message, Throwable t) {
        if (getShowNonSupportLevels()) {
            log(LOG_LEVEL_DEBUG, message, t);
        }
    }

    @Override
    public void error(String message) {
        if (getShowNonSupportLevels()) {
            log(LOG_LEVEL_ERROR, message, null);
        }
    }

    @Override
    public void error(String message, Throwable t) {
        if (getShowNonSupportLevels()) {
            log(LOG_LEVEL_ERROR, message, t);
        }
    }

    @Override
    public void fatal(String message) {
        if (getShowNonSupportLevels()) {
            log(LOG_LEVEL_FATAL, message, null);
        }
    }

    @Override
    public void fatal(String message, Throwable t) {
        if (getShowNonSupportLevels()) {
            log(LOG_LEVEL_FATAL, message, t);
        }
    }

    @Override
    public void info(String message) {
        if (getShowNonSupportLevels()) {
            log(LOG_LEVEL_INFO, message, null);
        }
    }

    @Override
    public void info(String message, Throwable t) {
        if (getShowNonSupportLevels()) {
            log(LOG_LEVEL_INFO, message, t);
        }
    }

    @Override
    public void warn(String message) {
        if (getShowNonSupportLevels()) {
            log(LOG_LEVEL_WARN, message, null);
        }
    }

    @Override
    public void warn(String message, Throwable t) {
        if (getShowNonSupportLevels()) {
            log(LOG_LEVEL_WARN, message, t);
        }
    }

    protected void log(int level, String message, Throwable t) {

        StringBuffer buf = new StringBuffer(32);

        if (getLevelInBrackets()) {
            buf.append('[');
        }

        // Append a readable representation of the log level
        switch (level) {
            case LOG_LEVEL_TRACE:
                buf.append(TRACE);
                break;
            case LOG_LEVEL_DEBUG:
                buf.append(DEBUG);
                break;
            case LOG_LEVEL_INFO:
                buf.append(INFO);
                break;
            case LOG_LEVEL_WARN:
                buf.append(WARN);
                break;
            case LOG_LEVEL_ERROR:
                buf.append(ERROR);
                break;
            case LOG_LEVEL_FATAL:
                buf.append(FATAL);
                break;
            default:
                buf.append(SUPPORT);
        }

        if (getLevelInBrackets()) {
            buf.append(']');
            buf.append(' ');
        }

        // Append date-time if so configured
        if (getShowDateTime() && getDateFormatter() != null) {
                buf.append(getFormattedDate());
                buf.append(' ');
        }

        // Append current thread name if so configured
        if (getShowThreadName()) {
            buf.append('[');
            buf.append(Thread.currentThread().getName());
            buf.append("] ");
        }

        // Append the name of the log instance if so configured
        if (getShowLogName()) {
            buf.append(String.valueOf(name)).append(" - ");
        }

        // Append the message
        buf.append(message);

        write(buf, t);

    }

    protected void write(StringBuffer buf, Throwable t) {
        System.out.println(buf.toString());
        if (t != null) {
            t.printStackTrace(System.out);
        }
        System.out.flush();
    }

    protected String getFormattedDate() {
        Date now = new Date();
        String dateText;
        synchronized (getDateFormatter()) {
            dateText = getDateFormatter().format(now);
        }
        return dateText;
    }

    protected boolean getShowDateTime() {
        String property = System.getProperty(SHOW_DATE_TIME_KEY, "true");
        return Boolean.valueOf(property);
    }

    protected DateFormat getDateFormatter() {
        String property = System.getProperty(DATE_TIME_FORMAT_KEY, "HH:mm:ss");
        return new SimpleDateFormat(property);
    }

    protected boolean getShowThreadName() {
        String property = System.getProperty(SHOW_THREAD_NAME_KEY, "false");
        return Boolean.valueOf(property);
    }

    protected boolean getShowLogName() {
        String property = System.getProperty(SHOW_LOG_NAME_KEY, "true");
        return Boolean.valueOf(property);
    }

    protected boolean getLevelInBrackets() {
        String property = System.getProperty(LEVEL_IN_BRACKETS_KEY, "true");
        return Boolean.valueOf(property);
    }

    protected boolean getShowNonSupportLevels() {
        String property = System.getProperty(SHOW_NON_SUPPORT_LEVELS_KEY, "false");
        return Boolean.valueOf(property);
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

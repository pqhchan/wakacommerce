
package com.wakacommerce.common.logging;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;

/**
 *
 * @ hui
 */
public class ProcessDetailLogger {

    private static final SupportLogger LOGGER = SupportLogManager.getLogger("ProcessLogging", ProcessDetailLogger.class);

    private Log processDetailLog;

    protected int listTemplateVariableMaxMemberCount = 30;

    protected int stringTemplateVariableMaxLength = 200;

    @Value("${ignore.no.process.detail.logger.configuration:false}")
    protected boolean ignoreNoProcessDetailLoggerConfiguration = false;

    @Value("${disable.all.process.detail.logging:false}")
    protected boolean disableAllProcessDetailLogging = false;

    public ProcessDetailLogger(String logIdentifier) {
        if (!disableAllProcessDetailLogging) {
            processDetailLog = LogFactory.getLog(logIdentifier);
            if (!ignoreNoProcessDetailLoggerConfiguration && !processDetailLog.isDebugEnabled()) {
                LOGGER.support("The system has detected that a ProcessDetailLogger instance was requested without " +
                        "backing " +
                        "logger configuration at the debug level. In this case, process detail logs may not be sent " +
                        "to the " +
                        "appropriate logging file, or may appear in an unwanted location, " +
                        "like the standard system log. You" +
                        "can disable this log message by setting the ignore.no.process.detail.logger.configuration " +
                        "property to true. A" +
                        "sample configuration for log4j (log4j.xml) that creates a rolling daily log looks like:\n\n" +
                        "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                        "<!DOCTYPE log4j:configuration SYSTEM \"log4j.dtd\">\n" +
                        "<log4j:configuration xmlns:log4j=\"http://jakarta.apache.org/log4j/\">\n" +
                        "<appender name=\"console\" class=\"org.apache.log4j.ConsoleAppender\">\n" +
                        "<param name=\"Target\" value=\"System.out\" />\n" +
                        "<layout class=\"org.apache.log4j.PatternLayout\">\n" +
                        "<param name=\"ConversionPattern\" value=\"[%5p] %d${HH:mm:ss$} %c${1$} - " +
                        "%m%n\" />\n" +
                        "</layout>\n" +
                        "</appender>\n" +
                        "<appender name=\"rollingDailyEnterpriseWorkflow\" class=\"org.apache.log4j" +
                        ".DailyRollingFileAppender\">\n" +
                        "<param name=\"file\" value=\"workflow.log\" />\n" +
                        "<param name=\"DatePattern\" value=\"'.'yyyy-MM-dd\" />\n" +
                        "<layout class=\"org.apache.log4j.PatternLayout\">\n" +
                        "<param name=\"ConversionPattern\" value=\"[%5p] %d${HH:mm:ss$} %c${1$} - " +
                        "%m%n\" />\n" +
                        "</layout>\n" +
                        "</appender>\n" +
                        "<logger name=\"com.broadleafcommerce.enterprise.workflow.process.detail\" " +
                        "additivity=\"false\">\n" +
                        "<level value=\"debug\"/>\n" +
                        "<appender-ref ref=\"rollingDailyEnterpriseWorkflow\"/>\n" +
                        "</logger>\n" +
                        "<root>\n" +
                        "<priority value=\"warn\" />\n" +
                        "<appender-ref ref=\"console\" />\n" +
                        "</root>\n" +
                        "</log4j:configuration>\n" +
                        "");
            }
        }
    }

    public void logProcessDetail(String logContext, String messageTemplate, Object... templateVariables) {
        if (!disableAllProcessDetailLogging && processDetailLog.isDebugEnabled()) {
            String message = String.format(messageTemplate, processVariables(templateVariables));
            logProcessDetail(logContext, message);
        }
    }

    public void logProcessDetail(String logContext, String message) {
        logProcessDetail(logContext, null, message);
    }

    public void logProcessDetail(String logContext, Throwable e, String messageTemplate, Object... templateVariables) {
        if (!disableAllProcessDetailLogging && processDetailLog.isDebugEnabled()) {
            String message = String.format(messageTemplate, processVariables(templateVariables));
            logProcessDetail(logContext, e, message);
        }
    }

    public void logProcessDetail(String logContext, Throwable e, String message) {
        if (!disableAllProcessDetailLogging && processDetailLog.isDebugEnabled()) {
            if (e == null) {
                processDetailLog.debug(logContext == null ? message : logContext + " " + message);
            } else {
                processDetailLog.debug(logContext == null ? message : logContext + " " + message, e);
            }
        }
    }

    protected Object[] processVariables(Object[] variables) {
        for (int j=0;j<variables.length;j++) {
            Object[] temp = null;
            if (variables[j] != null) {
                if (variables[j].getClass().isArray()) {
                    temp = (Object[]) variables[j];
                } else if (variables[j] instanceof Collection) {
                    temp = ((Collection) variables[j]).toArray(new Object[((Collection) variables[j]).size()]);
                }
            }
            if (temp != null) {
                String joined;
                if (temp.length > listTemplateVariableMaxMemberCount) {
                    Object[] shorten = new Object[listTemplateVariableMaxMemberCount];
                    System.arraycopy(temp, 0, shorten, 0, listTemplateVariableMaxMemberCount);
                    joined = StringUtils.join(shorten, ",");
                    joined += "...";
                } else {
                    joined = StringUtils.join(temp, ",");
                }
                variables[j] = joined;
            }
            if (variables[j] instanceof String && ((String) variables[j]).length() > stringTemplateVariableMaxLength) {
                variables[j] = ((String) variables[j]).substring(0, stringTemplateVariableMaxLength-1) + "...";
            }
        }
        return variables;
    }

    public int getListTemplateVariableMaxMemberCount() {
        return listTemplateVariableMaxMemberCount;
    }

    public void setListTemplateVariableMaxMemberCount(int listTemplateVariableMaxMemberCount) {
        this.listTemplateVariableMaxMemberCount = listTemplateVariableMaxMemberCount;
    }

    public int getStringTemplateVariableMaxLength() {
        return stringTemplateVariableMaxLength;
    }

    public void setStringTemplateVariableMaxLength(int stringTemplateVariableMaxLength) {
        this.stringTemplateVariableMaxLength = stringTemplateVariableMaxLength;
    }
}

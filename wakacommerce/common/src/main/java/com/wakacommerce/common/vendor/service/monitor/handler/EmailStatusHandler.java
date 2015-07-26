
package com.wakacommerce.common.vendor.service.monitor.handler;

import javax.annotation.Resource;

import com.wakacommerce.common.email.domain.EmailTarget;
import com.wakacommerce.common.email.service.EmailService;
import com.wakacommerce.common.email.service.info.EmailInfo;
import com.wakacommerce.common.vendor.service.monitor.StatusHandler;
import com.wakacommerce.common.vendor.service.type.ServiceStatusType;

public class EmailStatusHandler implements StatusHandler {

    @Resource(name="blEmailService")
    protected EmailService emailService;

    protected EmailInfo emailInfo;
    protected EmailTarget emailTarget;

    public void handleStatus(String serviceName, ServiceStatusType status) {
        String message = serviceName + " is reporting a status of " + status.getType();
        EmailInfo copy = emailInfo.clone();
        copy.setMessageBody(message);
        copy.setSubject(message);
        emailService.sendBasicEmail(copy, emailTarget, null);
    }

    public EmailInfo getEmailInfo() {
        return emailInfo;
    }

    public void setEmailInfo(EmailInfo emailInfo) {
        this.emailInfo = emailInfo;
    }

    public EmailTarget getEmailTarget() {
        return emailTarget;
    }

    public void setEmailTarget(EmailTarget emailTarget) {
        this.emailTarget = emailTarget;
    }

}

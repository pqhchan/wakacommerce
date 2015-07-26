
package com.wakacommerce.common.email.service;

import java.util.Map;

import com.wakacommerce.common.email.domain.EmailTarget;
import com.wakacommerce.common.email.service.info.EmailInfo;

/**
 * This null implementation class can be used during development to work around
 * the need to have a working SMTP service to route emails through.
 * 
 *aazzolini
 */
public class NullEmailServiceImpl implements EmailService {

    @Override
    public boolean sendTemplateEmail(String emailAddress, EmailInfo emailInfo, Map<String, Object> props) {
        return true;
    }

    @Override
    public boolean sendTemplateEmail(EmailTarget emailTarget, EmailInfo emailInfo, Map<String, Object> props) {
        return true;
    }

    @Override
    public boolean sendBasicEmail(EmailInfo emailInfo, EmailTarget emailTarget, Map<String, Object> props) {
        return true;
    }

}

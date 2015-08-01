package com.wakacommerce.common.email.service;

import java.util.Map;

import com.wakacommerce.common.email.domain.EmailTarget;
import com.wakacommerce.common.email.service.info.EmailInfo;

public interface EmailService {

    public boolean sendTemplateEmail(String emailAddress, EmailInfo emailInfo,  Map<String,Object> props);

    public boolean sendTemplateEmail(EmailTarget emailTarget, EmailInfo emailInfo, Map<String,Object> props);

    public boolean sendBasicEmail(EmailInfo emailInfo, EmailTarget emailTarget, Map<String,Object> props);

}

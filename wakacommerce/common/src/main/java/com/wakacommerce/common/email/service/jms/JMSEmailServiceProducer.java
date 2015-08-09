package com.wakacommerce.common.email.service.jms;

import org.springframework.jms.core.JmsTemplate;

import com.wakacommerce.common.email.service.message.EmailServiceProducer;

import javax.jms.Destination;

public interface JMSEmailServiceProducer extends EmailServiceProducer {

    public JmsTemplate getEmailServiceTemplate();

    public void setEmailServiceTemplate(JmsTemplate emailServiceTemplate);

    public Destination getEmailServiceDestination();

    public void setEmailServiceDestination(Destination emailServiceDestination);

}


package com.wakacommerce.common.email.service.jms;

import org.springframework.jms.core.JmsTemplate;

import com.wakacommerce.common.email.service.message.EmailServiceProducer;

import javax.jms.Destination;

public interface JMSEmailServiceProducer extends EmailServiceProducer {

    /**
     * @return the emailServiceTemplate
     */
    public JmsTemplate getEmailServiceTemplate();

    /**
     * @param emailServiceTemplate the emailServiceTemplate to set
     */
    public void setEmailServiceTemplate(JmsTemplate emailServiceTemplate);

    /**
     * @return the emailServiceDestination
     */
    public Destination getEmailServiceDestination();

    /**
     * @param emailServiceDestination the emailServiceDestination to set
     */
    public void setEmailServiceDestination(Destination emailServiceDestination);

}

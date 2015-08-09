 
package com.wakacommerce.cms.page.message.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.wakacommerce.cms.page.domain.Page;
import com.wakacommerce.cms.page.message.ArchivedPagePublisher;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 *
 * @ hui
 */
public class JMSArchivedPagePublisher implements ArchivedPagePublisher {

    private JmsTemplate archivePageTemplate;

    private Destination archivePageDestination;

    @Override
    public void processPageArchive(final Page page, final String basePageKey) {
        archivePageTemplate.send(archivePageDestination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(basePageKey);
            }
        });
    }

    public JmsTemplate getArchivePageTemplate() {
        return archivePageTemplate;
    }

    public void setArchivePageTemplate(JmsTemplate archivePageTemplate) {
        this.archivePageTemplate = archivePageTemplate;
    }

    public Destination getArchivePageDestination() {
        return archivePageDestination;
    }

    public void setArchivePageDestination(Destination archivePageDestination) {
        this.archivePageDestination = archivePageDestination;
    }
}

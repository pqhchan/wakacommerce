 
package com.wakacommerce.cms.structure.message.jms;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import com.wakacommerce.cms.structure.domain.StructuredContent;
import com.wakacommerce.cms.structure.message.ArchivedStructuredContentPublisher;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.HashMap;

/**
 * JMS implementation of ArchivedPagePublisher.
 * Intended usage is to notify other VMs that a pageDTO needs to be
 * evicted from cache.   This occurs when the page is marked as
 * archived - typically because a replacemet page has been
 * promoted to production.
 *
 * Utilizes Spring JMS template pattern where template and destination
 * are configured via Spring.
 *
 *   
 */
public class JMSArchivedStructuredContentPublisher implements ArchivedStructuredContentPublisher {

    private JmsTemplate archiveStructuredContentTemplate;

    private Destination archiveStructuredContentDestination;

    @Override
    public void processStructuredContentArchive(final StructuredContent sc, final String baseNameKey, final String baseTypeKey) {
        archiveStructuredContentTemplate.send(archiveStructuredContentDestination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                HashMap<String, String> objectMap = new HashMap<String,String>(2);
                objectMap.put("nameKey", baseNameKey);
                objectMap.put("typeKey", baseTypeKey);
                return session.createObjectMessage(objectMap);
            }
        });
    }

    public JmsTemplate getArchiveStructuredContentTemplate() {
        return archiveStructuredContentTemplate;
    }

    public void setArchiveStructuredContentTemplate(JmsTemplate archiveStructuredContentTemplate) {
        this.archiveStructuredContentTemplate = archiveStructuredContentTemplate;
    }

    public Destination getArchiveStructuredContentDestination() {
        return archiveStructuredContentDestination;
    }

    public void setArchiveStructuredContentDestination(Destination archiveStructuredContentDestination) {
        this.archiveStructuredContentDestination = archiveStructuredContentDestination;
    }
}

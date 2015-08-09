 
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
 *
 * @ hui
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

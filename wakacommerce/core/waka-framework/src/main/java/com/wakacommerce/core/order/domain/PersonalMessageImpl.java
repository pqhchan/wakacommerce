
package com.wakacommerce.core.order.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.wakacommerce.common.presentation.AdminPresentation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PERSONAL_MESSAGE")
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region="blOrderElements")
public class PersonalMessageImpl implements PersonalMessage {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "PersonalMessageId")
    @GenericGenerator(
        name="PersonalMessageId",
        strategy="com.wakacommerce.common.persistence.IdOverrideTableGenerator",
        parameters = {
            @Parameter(name="segment_value", value="PersonalMessageImpl"),
            @Parameter(name="entity_name", value="com.wakacommerce.core.order.domain.PersonalMessageImpl")
        }
    )
    @Column(name = "PERSONAL_MESSAGE_ID")
    protected Long id;

    @Column(name = "MESSAGE_TO")
    @AdminPresentation(friendlyName = "PersonalMessageImpl_Message_To", order=1, group = "PersonalMessageImpl_Personal_Message")
    protected String messageTo;

    @Column(name = "MESSAGE_FROM")
    @AdminPresentation(friendlyName = "PersonalMessageImpl_Message_From", order=2, group = "PersonalMessageImpl_Personal_Message")
    protected String messageFrom;

    @Column(name = "MESSAGE")
    @AdminPresentation(friendlyName = "PersonalMessageImpl_Message", order=3, group = "PersonalMessageImpl_Personal_Message")
    protected String message;

    @Column(name = "OCCASION")
    @AdminPresentation(friendlyName = "PersonalMessageImpl_Occasion", order=4, group = "PersonalMessageImpl_Personal_Message")
    protected String occasion;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getMessageTo() {
        return messageTo;
    }

    @Override
    public void setMessageTo(String messageTo) {
        this.messageTo = messageTo;
    }

    @Override
    public String getMessageFrom() {
        return messageFrom;
    }

    @Override
    public void setMessageFrom(String messageFrom) {
        this.messageFrom = messageFrom;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String getOccasion() {
        return occasion;
    }

    @Override
    public void setOccasion(String occasion) {
        this.occasion = occasion;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((messageFrom == null) ? 0 : messageFrom.hashCode());
        result = prime * result + ((messageTo == null) ? 0 : messageTo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!getClass().isAssignableFrom(obj.getClass()))
            return false;
        PersonalMessageImpl other = (PersonalMessageImpl) obj;

        if (id != null && other.id != null) {
            return id.equals(other.id);
        }

        if (message == null) {
            if (other.message != null)
                return false;
        } else if (!message.equals(other.message))
            return false;
        if (messageFrom == null) {
            if (other.messageFrom != null)
                return false;
        } else if (!messageFrom.equals(other.messageFrom))
            return false;
        if (messageTo == null) {
            if (other.messageTo != null)
                return false;
        } else if (!messageTo.equals(other.messageTo))
            return false;
        return true;
    }
}

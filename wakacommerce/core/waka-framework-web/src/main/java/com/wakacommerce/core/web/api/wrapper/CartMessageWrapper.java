
package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.core.order.service.call.ActivityMessageDTO;

@XmlRootElement(name = "message")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class CartMessageWrapper extends BaseWrapper implements APIWrapper<ActivityMessageDTO> {

    @XmlElement
    protected String message;
    @XmlElement
    protected String messageType;
    @XmlElement
    protected Integer priority;
    @XmlElement
    protected String errorCode;
    @Override
    public void wrapDetails(ActivityMessageDTO model, HttpServletRequest request) {
        this.message = model.getMessage();
        this.priority = model.getPriority();
        this.messageType = model.getType();
        this.errorCode = model.getErrorCode();
    }

    @Override
    public void wrapSummary(ActivityMessageDTO model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    
    /**
     * @return the messageType
     */
    public String getMessageType() {
        return messageType;
    }

    
    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    
    /**
     * @return the priority
     */
    public Integer getPriority() {
        return priority;
    }

    
    /**
     * @param priority the priority to set
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    
    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    
    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}

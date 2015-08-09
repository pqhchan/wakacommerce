
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

}

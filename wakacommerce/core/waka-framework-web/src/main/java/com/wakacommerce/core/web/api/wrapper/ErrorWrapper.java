
package com.wakacommerce.core.web.api.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "error")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class ErrorWrapper extends BaseWrapper {

    /*
     * This is in case the client prefers to return a 200 with with this error, but 
     * wants to represent the error code, say a 500, here.
     */
    @XmlElement
    protected Integer httpStatusCode;

    @XmlElementWrapper(name = "messages")
    @XmlElement(name = "message")
    protected List<ErrorMessageWrapper> messages;

    public Integer getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public List<ErrorMessageWrapper> getMessages() {
        if (this.messages == null) {
            this.messages = new ArrayList<ErrorMessageWrapper>();
        }
        return this.messages;
    }

    public void setMessages(List<ErrorMessageWrapper> messages) {
        this.messages = messages;
    }

}


package com.wakacommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.wakacommerce.common.WakaEnumType;

/**
 * This is a JAXB wrapper around HibuProduct.

 */
@XmlRootElement(name = "BroadleafEnumerationTypeWrapper")
@XmlAccessorType(value = XmlAccessType.FIELD)
public class BroadleafEnumerationTypeWrapper extends BaseWrapper implements APIWrapper<WakaEnumType> {


    @XmlElement
    protected String friendlyName;

    @XmlElement
    protected String type;

    @Override
    public void wrapDetails(WakaEnumType model, HttpServletRequest request) {
        if (model == null) return;
        this.friendlyName = model.getFriendlyType();
        this.type = model.getType();
    }

    @Override
    public void wrapSummary(WakaEnumType model, HttpServletRequest request) {
        wrapDetails(model, request);
    }

    
    /**
     * @return the friendlyName
     */
    public String getFriendlyName() {
        return friendlyName;
    }

    
    /**
     * @param friendlyName the friendlyName to set
     */
    public void setFriendlyName(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    
    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    
    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}

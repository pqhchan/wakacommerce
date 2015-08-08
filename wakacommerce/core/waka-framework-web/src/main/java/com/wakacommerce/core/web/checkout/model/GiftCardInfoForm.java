  
package com.wakacommerce.core.web.checkout.model;

import java.io.Serializable;

/**
 *   (jocanas)
 */
public class GiftCardInfoForm implements Serializable {

    private static final long serialVersionUID = 1L;

    private String giftCardNumber;
    private String giftCardEmailAddress;

    public String getGiftCardNumber() {
        return giftCardNumber;
    }

    public void setGiftCardNumber(String giftCardNumber) {
        this.giftCardNumber = giftCardNumber;
    }

    public String getGiftCardEmailAddress() {
        return giftCardEmailAddress;
    }

    public void setGiftCardEmailAddress(String giftCardEmailAddress) {
        this.giftCardEmailAddress = giftCardEmailAddress;
    }

}

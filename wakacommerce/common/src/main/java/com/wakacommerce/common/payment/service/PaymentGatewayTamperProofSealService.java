

package com.wakacommerce.common.payment.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @ hui
 */
public interface PaymentGatewayTamperProofSealService {

    public String createTamperProofSeal(String secretKey, String customerId, String orderId)
            throws NoSuchAlgorithmException, InvalidKeyException;

    public Boolean verifySeal(String seal, String secretKey, String customerId, String orderId)
            throws InvalidKeyException, NoSuchAlgorithmException;

}

package com.wakacommerce.common.encryption;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wakacommerce.common.config.RuntimeEnvironmentKeyResolver;
import com.wakacommerce.common.config.SystemPropertyRuntimeEnvironmentKeyResolver;

/**
 *
 * @ hui
 */
public class PassthroughEncryptionModule implements EncryptionModule {

    private static final Log LOG = LogFactory.getLog(PassthroughEncryptionModule.class);

    protected RuntimeEnvironmentKeyResolver keyResolver = new SystemPropertyRuntimeEnvironmentKeyResolver();

    public PassthroughEncryptionModule() {
        if ("production".equals(keyResolver.resolveRuntimeEnvironmentKey())) {
            LOG.warn("This passthrough encryption module provides NO ENCRYPTION and should NOT be used in production.");
        }
    }

    @Override
    public String decrypt(String cipherText) {
        return cipherText;
    }

    @Override
    public String encrypt(String plainText) {
        return plainText;
    }

}

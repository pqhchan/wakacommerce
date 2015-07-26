
package com.wakacommerce.common.encryption;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wakacommerce.common.config.RuntimeEnvironmentKeyResolver;
import com.wakacommerce.common.config.SystemPropertyRuntimeEnvironmentKeyResolver;

/**
 * The default encryption module simply passes through the decrypt and encrypt text.
 * A real implementation should adhere to PCI compliance, which requires robust key
 * management, including regular key rotation. An excellent solution would be a module
 * for interacting with the StrongKey solution. Refer to this discussion:
 *
 * http://www.strongauth.com/forum/index.php?topic=44.0
 *
 *jfischer
 *
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

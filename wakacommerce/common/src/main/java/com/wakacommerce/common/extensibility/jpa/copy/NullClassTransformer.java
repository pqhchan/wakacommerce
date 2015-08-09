
package com.wakacommerce.common.extensibility.jpa.copy;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Properties;

import com.wakacommerce.common.extensibility.jpa.convert.BroadleafClassTransformer;

/**
 *
 * @ hui
 */
public class NullClassTransformer implements BroadleafClassTransformer {
    
    @Override
    public void compileJPAProperties(Properties props, Object key) throws Exception {
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, 
            ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return null;
    }

}


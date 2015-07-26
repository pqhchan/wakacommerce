
package com.wakacommerce.common.extensibility.jpa.copy;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Properties;

import com.wakacommerce.common.extensibility.jpa.convert.BroadleafClassTransformer;

/**
 * This class transformer will do nothing. The main use case for this transformer is when you would prefer to not
 * have a module's template classes copied over, and would rather do it yourself. 
 * 
 * This transformer should not typically be used.
 * 
 *Andre Azzolini (apazzolini)
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

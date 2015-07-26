
package com.wakacommerce.common.extensibility.jpa.convert;

import javax.persistence.spi.ClassTransformer;
import java.util.Properties;

/**
 * 
 *jfischer
 *
 */
public interface BroadleafClassTransformer extends ClassTransformer {

    public void compileJPAProperties(Properties props, Object key) throws Exception;
        
}

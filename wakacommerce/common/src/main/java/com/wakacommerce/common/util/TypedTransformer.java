
package com.wakacommerce.common.util;

import org.apache.commons.collections.Transformer;


/**
 *
 * @ hui
 */
public interface TypedTransformer<K> extends Transformer {
    
    public K transform(Object input);

}

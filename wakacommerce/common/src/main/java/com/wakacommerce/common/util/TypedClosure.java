
package com.wakacommerce.common.util;


/**
 *
 * @ hui
 */
public interface TypedClosure<K, V> {
    
    public K getKey(V value);

}


package com.wakacommerce.common.util;


/**
 * A class that provides for a typed closure that will return a the specified type value from the specified type
 * 
 *Andre Azzolini (apazzolini)
 *
 * @param <K> the type of the key to be returned
 * @param <V> the type of the value to generate a key for
 */
public interface TypedClosure<K, V> {
    
    public K getKey(V value);

}

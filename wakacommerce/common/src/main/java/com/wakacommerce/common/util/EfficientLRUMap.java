
package com.wakacommerce.common.util;

import org.apache.commons.collections4.map.LRUMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @ hui
 */
public class EfficientLRUMap<K, V> implements Map<K, V> {

    private Map<K, V> concurrentMap;
    private Map<K, V> lruMap;
    private int maxEntries;
    private boolean usingLRUMap = false;
    
    public EfficientLRUMap(int maxEntries) {
        this.maxEntries = maxEntries;
        concurrentMap = new ConcurrentHashMap<K, V>();
    }

    @Override
    public int size() {
        if (usingLRUMap) {
            return lruMap.size();
        } else {
            return concurrentMap.size();
        }
    }

    @Override
    public boolean isEmpty() {
        if (usingLRUMap) {
            return lruMap.isEmpty();
        } else {
            return concurrentMap.isEmpty();
        }
    }

    @Override
    public boolean containsKey(Object key) {
        if (usingLRUMap) {
            return lruMap.containsKey(key);
        } else {
            return concurrentMap.containsKey(key);
        }
    }

    @Override
    public boolean containsValue(Object value) {
        if (usingLRUMap) {
            return lruMap.containsValue(value);
        } else {
            return concurrentMap.containsValue(value);
        }
    }

    @Override
    public V get(Object key) {
        if (usingLRUMap) {
            return lruMap.get(key);
        } else {
            return concurrentMap.get(key);
        }
    }

    @Override
    public V put(K key, V value) {
        if (usingLRUMap) {
            return lruMap.put(key, value);
        } else {
            V returnVal = concurrentMap.put(key, value);
            if (switchToLRUMap()) {
                // The switch could have happened on another thread.
                if (!lruMap.containsKey(key)) {
                    lruMap.put(key, value);
                }
            }
            return returnVal;
        }
    }

    protected synchronized boolean switchToLRUMap() {
        if (!usingLRUMap) {
            if (size() > maxEntries) {
                lruMap = Collections.synchronizedMap(new LRUMap<K, V>(maxEntries));
                lruMap.putAll(concurrentMap);
                usingLRUMap = true;
                concurrentMap.clear();
            }
        }
        return usingLRUMap; // this could be set by another thread        
    }

    @Override
    public V remove(Object key) {
        if (usingLRUMap) {
            // This could put us back below the threshold for LRU vs. Concurrent but we won't optimize to that
            // level as we are likely to thrash going back and forth.    Once an LRU, always an LRU unless clear
            // is called.
            return lruMap.remove(key);
        } else {
            return concurrentMap.remove(key);
        }
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        if (usingLRUMap) {
            lruMap.putAll(m);
        } else {
            concurrentMap.putAll(m);
            if (switchToLRUMap()) {
                // The switch could have happened on another thread.                
                lruMap.putAll(m);
            }
        }
    }

    @Override
    public void clear() {
        if (usingLRUMap) {
            resetInternalMap();
        } else {
            concurrentMap.clear();
        }
    }

    protected synchronized void resetInternalMap() {
        usingLRUMap = false;
        lruMap.clear();
    }

    @Override
    public Set<K> keySet() {
        if (usingLRUMap) {
            return lruMap.keySet();
        } else {
            return concurrentMap.keySet();
        }
    }

    @Override
    public Collection<V> values() {
        if (usingLRUMap) {
            return lruMap.values();
        } else {
            return concurrentMap.values();
        }
    }

    @Override
    public Set<java.util.Map.Entry<K, V>> entrySet() {
        if (usingLRUMap) {
            return lruMap.entrySet();
        } else {
            return concurrentMap.entrySet();
        }
    }
    
    protected Class getUnderlyingMapClass() {
        if (usingLRUMap) {
            return lruMap.getClass();
        } else {
            return concurrentMap.getClass();
        }
    }
}

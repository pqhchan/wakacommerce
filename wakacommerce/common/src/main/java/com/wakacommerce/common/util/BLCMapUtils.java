
package com.wakacommerce.common.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;


/**
 *
 * @ hui
 */
public class BLCMapUtils {

    public static <K, CV extends Iterable<V>, V> Map<K, V> keyedMap(CV values, TypedClosure<K, V> closure) {
        Map<K, V> map = new HashMap<K, V>();
        
        for (V value : values) {
            K key = closure.getKey(value);
            map.put(key, value);
        }
        
        return map;
    }

    public static <K, V> Map<K, V> keyedMap(V[] values, TypedClosure<K, V> closure) {
        Map<K, V> map = new HashMap<K, V>();
        
        if (values != null) {
            for (V value : values) {
                K key = closure.getKey(value);
                map.put(key, value);
            }
        }
        
        return map;
    }

    public static <K, CV extends Iterable<V>, V> Map<K, List<V>> keyedListMap(CV values, TypedClosure<K, V> closure) {
        Map<K, List<V>> map = new HashMap<K, List<V>>();
        
        for (V value : values) {
            K key = closure.getKey(value);
            List<V> list = map.get(key);
            if (list == null) {
                list = new ArrayList<V>();
                map.put(key, list);
            }
            list.add(value);
        }
        
        return map;
    }
    
    public static <K, V> Map<K, V> valueSortedMap(Map<K, V> map, Comparator<Entry<K, V>> comparator) {
        Set<Entry<K, V>> valueSortedEntries = new TreeSet<Entry<K, V>>(comparator);
        
        for (Entry<K, V> entry : map.entrySet()) {
            valueSortedEntries.add(entry);
        }
        
        Map<K, V> sortedMap = new LinkedHashMap<K, V>(map.size());
        for (Entry<K, V> entry : valueSortedEntries) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }
        
        return sortedMap;
    }

}


package com.wakacommerce.common.util;

import java.util.ArrayList;
import java.util.HashSet;



/**
 *
 * @ hui
 */
public class BLCArrayUtils {

    public static <T> boolean contains(T[] array, TypedPredicate<T> predicate) {
        for (T o : array) {
            if (predicate.evaluate(o)) {
                return true;
            }
        }
        return false;
    }

    public static <T> ArrayList<T> asList(T[] array) {
        if (array == null) {
            return null;
        }
        ArrayList<T> list = new ArrayList<T>(array.length);
        for (T e : array) {
            list.add(e);
        }
        return list;
    }

    public static <T, O> ArrayList<T> collect(Object[] array, TypedTransformer<T> transformer) {
        ArrayList<T> list = new ArrayList<T>(array.length);
        for (Object o : array) {
            list.add(transformer.transform(o));
        }
        return list;
    }

    public static <T, O> HashSet<T> collectSet(Object[] array, TypedTransformer<T> transformer) {
        HashSet<T> set = new HashSet<T>(array.length);
        for (Object o : array) {
            set.add(transformer.transform(o));
        }
        return set;
    }

}

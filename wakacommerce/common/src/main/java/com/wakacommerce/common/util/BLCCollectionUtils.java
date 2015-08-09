
package com.wakacommerce.common.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @ hui
 */
public class BLCCollectionUtils {

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> Collection<T> collect(Collection inputCollection, TypedTransformer<T> transformer) {
        return CollectionUtils.collect(inputCollection, transformer);
    }

    @SuppressWarnings("rawtypes")
    public static <T> List<T> collectList(Collection inputCollection, TypedTransformer<T> transformer) {
        List<T> returnList = new ArrayList<T>();
        for (Object obj : inputCollection) {
            T transformed = transformer.transform(obj);
            returnList.add(transformed);
        }
        return returnList;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T> T[] collectArray(Collection inputCollection, TypedTransformer<T> transformer, Class<T> clazz) {
        T[] returnArray = (T[]) Array.newInstance(clazz, inputCollection.size());
        int i = 0;
        for (Object obj : inputCollection) {
            T transformed = transformer.transform(obj);
            returnArray[i++] = transformed;
        }
        return returnArray;
    }

    public static <T> List<T> selectList(Collection<T> inputCollection, TypedPredicate<T> predicate) {
        ArrayList<T> answer = new ArrayList<T>(inputCollection.size());
        CollectionUtils.select(inputCollection, predicate, answer);
        return answer;
    }

    public static <T> List<T> createIfNull(List<T> list) {
        return (list == null) ? new ArrayList<T>() : list;
    }

    public static <T extends Collection> T createChangeAwareCollection(final WorkOnChange work, final Collection original) {
        T proxy = (T) Proxy.newProxyInstance(BLCCollectionUtils.class.getClassLoader(), ClassUtils.getAllInterfacesForClass(original.getClass()), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (method.getName().startsWith("add") || method.getName().startsWith("remove") || method.getName()
                        .startsWith("clear")) {
                    work.doWork(original);
                }
                if (method.getName().equals("iterator")) {
                    final Iterator itr = (Iterator) method.invoke(original, args);
                    Iterator proxyItr = (Iterator) Proxy.newProxyInstance(getClass().getClassLoader(), ClassUtils.getAllInterfacesForClass(itr.getClass()), new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (method.getName().equals("remove")) {
                                work.doWork(original);
                            }
                            return method.invoke(itr, args);
                        }
                    });
                    return proxyItr;
                }
                return method.invoke(original, args);
            }
        });
        return proxy;
    }
}


package com.wakacommerce.common.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @ hui
 */
public class InvocationHandlerForUnitTestingByteWeavedClasses implements InvocationHandler {

    public static <T> T createProxy(Class<T> proxyType, Class<?>[] interfaces, Object[] objectsForByteWeaving) {
        InvocationHandler handler = new InvocationHandlerForUnitTestingByteWeavedClasses(objectsForByteWeaving);
        return (T) Proxy.newProxyInstance(handler.getClass().getClassLoader(), interfaces, handler);
    }

    protected Object[] objectsForByteWeaving;

    public InvocationHandlerForUnitTestingByteWeavedClasses(Object[] objectsForByteWeaving) {
        this.objectsForByteWeaving = objectsForByteWeaving;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        
        for (Object object : objectsForByteWeaving) {
            try {
                return method.invoke(object, args);
            } catch (IllegalArgumentException exception) {
                continue;
            }
        }

        return null;
    }

    public Object[] getObjectsForByteWeaving() {
        return objectsForByteWeaving;
    }

    public void setObjectsForByteWeaving(Object[] objects) {
        this.objectsForByteWeaving = objects;
    }

}

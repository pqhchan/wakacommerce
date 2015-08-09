
package com.wakacommerce.common.util;

import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;

import com.wakacommerce.common.exception.ExceptionHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @ hui
 */
public class ReflectiveWorkOnChange implements WorkOnChange {

    private static Map<String, Method> methodCache = new EfficientLRUMap<String, Method>(1000);

    private final Object target;
    private final String methodName;

    public ReflectiveWorkOnChange(Object target, String methodName) {
        this.target = target;
        this.methodName = methodName;
    }

    @Override
    public void doWork(Collection changed) {
        String key = target.getClass().getName() + "." + methodName + "(" + changed.getClass().getName() + ")";
        Method method = methodCache.get(key);
        if (method == null) {
            method = searchForMethod(target.getClass(), changed);
            if (method != null) {
                methodCache.put(key, method);
            }
        }
        if (method == null) {
            throw new IllegalArgumentException("Unable to find the method (" + methodName + ") on the class (" + target.getClass().getName() + ")");
        }
        try {
            method.invoke(target, changed);
        } catch (IllegalAccessException e) {
            throw ExceptionHelper.refineException(e);
        } catch (InvocationTargetException e) {
            throw ExceptionHelper.refineException(e);
        }
    }

    protected Method searchForMethod(Class<?> targetClass, Object test) {
        Method method = ReflectionUtils.findMethod(target.getClass(), methodName, test.getClass());
        if (method == null) {
            Class[] interfaces = ClassUtils.getAllInterfaces(test);
            for (Class clazz : interfaces) {
                method = ReflectionUtils.findMethod(targetClass, methodName, clazz);
                if (method != null) {
                    break;
                }
            }
        }
        return method;
    }
}

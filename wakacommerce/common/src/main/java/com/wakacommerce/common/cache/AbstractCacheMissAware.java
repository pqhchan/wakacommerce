
package com.wakacommerce.common.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.springframework.util.ClassUtils;

import com.wakacommerce.common.sandbox.domain.SandBox;
import com.wakacommerce.common.web.WakaRequestContext;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public abstract class AbstractCacheMissAware {
    
    @Resource(name="blStatisticsService")
    protected StatisticsService statisticsService;

    protected Cache cache;

    private Object nullObject = null;

    protected String buildKey(String... params) {
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
        SandBox sandBox = context.getSandBox();
        String key = StringUtils.join(params, '_');
        if (sandBox != null) {
            key = sandBox.getId() + "_" + key;
        }
        return key;
    }

    protected <T> T getObjectFromCache(String key, String cacheName) {
        Element cacheElement = getCache(cacheName).get(key);
        if (cacheElement != null) {
            return (T) cacheElement.getValue();
        }
        return null;
    }

    protected Cache getCache(String cacheName) {
        if (cache == null) {
            cache = CacheManager.getInstance().getCache(cacheName);
        }
        return cache;
    }

    protected void removeItemFromCache(String cacheName, String... params) {
        String key = buildKey(params);
        if (getLogger().isTraceEnabled()) {
            getLogger().trace("Evicting [" + key + "] from the [" + cacheName + "] cache.");
        }
        getCache(cacheName).remove(key);
    }

    protected void clearCache(String cacheName) {
        if (getLogger().isTraceEnabled()) {
            getLogger().trace("Evicting all keys from the [" + cacheName + "] cache.");
        }
        getCache(cacheName).removeAll();
    }

    protected synchronized <T> T getNullObject(final Class<T> responseClass) {
        if (nullObject == null) {
            Class<?>[] interfaces = (Class<?>[]) ArrayUtils.add(ClassUtils.getAllInterfacesForClass(responseClass), Serializable.class);
            nullObject = Proxy.newProxyInstance(getClass().getClassLoader(), interfaces, new InvocationHandler() {
                @Override
                public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                    if (method.getName().equals("equals")) {
                        return !(objects[0] == null) && objects[0].hashCode() == 31;
                    } else if (method.getName().equals("hashCode")) {
                        return 31;
                    } else if (method.getName().equals("toString")) {
                        return "Null_" + responseClass.getSimpleName();
                    }
                    throw new IllegalAccessException("Not a real object");
                }
            });
        }
        return (T) nullObject;
    }

    protected <T> T getCachedObject(Class<T> responseClass, String cacheName, String statisticsName, PersistentRetrieval<T> retrieval, String... params) {
        T nullResponse = getNullObject(responseClass);
        WakaRequestContext context = WakaRequestContext.getWakaRequestContext();
        String key = buildKey(params);
        T response = null;
        if (context.isProductionSandBox() || (context.getAdditionalProperties().containsKey("allowLevel2Cache") && (Boolean) context.getAdditionalProperties().get("allowLevel2Cache"))) {
            response = getObjectFromCache(key, cacheName);
        }
        if (response == null) {
            response = retrieval.retrievePersistentObject();
            if (response == null) {
                response = nullResponse;
            }
            //only handle null, non-hits. Otherwise, let level 2 cache handle it
            if ((context.isProductionSandBox() || (context.getAdditionalProperties().containsKey("allowLevel2Cache") && (Boolean) context.getAdditionalProperties().get("allowLevel2Cache"))) && response.equals(nullResponse)) {
                statisticsService.addCacheStat(statisticsName, false);
                getCache(cacheName).put(new Element(key, response));
                if (getLogger().isTraceEnabled()) {
                    getLogger().trace("Caching [" + key + "] as null in the [" + cacheName + "] cache.");
                }
            }
        } else {
            statisticsService.addCacheStat(statisticsName, true);
        }
        if (response.equals(nullResponse)) {
            return null;
        }
        return response;
    }

    protected abstract Log getLogger();
}

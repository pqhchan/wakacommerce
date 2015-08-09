
package com.wakacommerce.common.util;

import org.hibernate.proxy.HibernateProxy;
import org.hibernate.proxy.LazyInitializer;

/**
 *
 * @ hui
 */
public class HibernateUtils {

    public static <T> T deproxy(T t) {
        if (t instanceof HibernateProxy) {
            HibernateProxy proxy = (HibernateProxy)t;
            LazyInitializer lazyInitializer = proxy.getHibernateLazyInitializer();
            return (T)lazyInitializer.getImplementation();
        }
        return t;
    }
}

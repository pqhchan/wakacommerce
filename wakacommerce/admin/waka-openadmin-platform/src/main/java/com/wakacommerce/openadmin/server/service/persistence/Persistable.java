
package com.wakacommerce.openadmin.server.service.persistence;

/**
 *
 * @ hui
 */
public interface Persistable<T, G extends Throwable> {

    public T execute() throws G;

}

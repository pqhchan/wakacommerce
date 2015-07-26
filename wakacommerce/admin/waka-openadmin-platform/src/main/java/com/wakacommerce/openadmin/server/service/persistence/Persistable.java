
package com.wakacommerce.openadmin.server.service.persistence;

/**
 *Jeff Fischer
 */
public interface Persistable<T, G extends Throwable> {

    public T execute() throws G;

}

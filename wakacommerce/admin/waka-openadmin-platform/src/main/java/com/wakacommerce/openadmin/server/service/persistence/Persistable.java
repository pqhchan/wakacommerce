
package com.wakacommerce.openadmin.server.service.persistence;

/**
 * 
 */
public interface Persistable<T, G extends Throwable> {

    public T execute() throws G;

}

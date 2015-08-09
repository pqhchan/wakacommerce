
package com.wakacommerce.common.util;

/**
 *
 * @ hui
 */
public interface StreamCapableTransactionalOperation extends TransactionalOperation {

    void pagedExecute(Object[] param) throws Throwable;

    Object[] retrievePage(int startPos, int pageSize);

    Long retrieveTotalCount();

    void executeAfterCommit(Object[] param);

    boolean shouldRetryOnTransactionLockAcquisitionFailure();

    int retryMaxCountOverrideForLockAcquisitionFailure();

}


package com.wakacommerce.core.workflow.state;

import com.wakacommerce.common.classloader.release.ThreadLocalManager;

/**
 *
 * @ hui
 */
public class RollbackStateLocal {

    private static final ThreadLocal<RollbackStateLocal> THREAD_LOCAL = ThreadLocalManager.createThreadLocal(RollbackStateLocal.class, false);

    public static RollbackStateLocal getRollbackStateLocal() {
        return THREAD_LOCAL.get();
    }

    public static void setRollbackStateLocal(RollbackStateLocal rollbackStateLocal) {
        THREAD_LOCAL.set(rollbackStateLocal);
    }

    private String threadId;
    private String workflowId;

    public String getThreadId() {
        return threadId;
    }

    public void setThreadId(String threadId) {
        this.threadId = threadId;
    }

    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }
}


package com.wakacommerce.core.util;

import com.wakacommerce.core.workflow.BaseActivity;
import com.wakacommerce.core.workflow.ProcessContext;

/**
 *
 * @ hui
 */
public class ThirdPartyInteractionLatencySimulationActivity extends BaseActivity<ProcessContext<Object>> {

    private long waitTime = 1000L;

    @Override
    public ProcessContext<Object> execute(ProcessContext<Object> context) throws Exception {
        try {
            Thread.sleep(waitTime);
        } catch (Throwable e) {
            //do nothing
        }

        return context;
    }

    public long getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }
}

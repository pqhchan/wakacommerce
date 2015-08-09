
package com.wakacommerce.core.workflow;



/**
 *
 * @ hui
 */
public class PassThroughActivity extends BaseActivity<ProcessContext<? extends Object>> {

    @Override
    public ProcessContext<? extends Object> execute(ProcessContext<? extends Object> context) throws Exception {
        return context;
    }

}


package com.wakacommerce.core.workflow;


/**
 * <p>
 * Marker interface that all modules should use when adding activities to Broadleaf workflows. This is used for logging to
 * the user on startup that a module has modified a particular workflow and the final ordering of the configured workflow.
 * This logging is necessary for users that might be unaware of all of the activities that different modules could be
 * injecting into their workflows, since it's possible they they might want to change the ordering of their particular
 * activities as well.</p>
 * 
 * <p>When writing a module activity, the ordering should be configured in the 100 range (3100, 3200, etc) so that users
 * who use your module can configure custom activities in-between framework <b>and</b> module activities.</p>
 * 
 *     
 */
public interface ModuleActivity {

    /**
     * The name of the module that this activity came from (for instance: 'inventory')
     * @return
     */
    public String getModuleName();

}

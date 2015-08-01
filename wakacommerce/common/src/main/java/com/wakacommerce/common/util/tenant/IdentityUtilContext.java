
package com.wakacommerce.common.util.tenant;

import java.util.Stack;

import com.wakacommerce.common.classloader.release.ThreadLocalManager;
import com.wakacommerce.common.site.domain.Site;

/**
 * A thread local context to store the unique name for this request thread.
 *
 * 
 */
public class IdentityUtilContext extends Stack<IdentityUtilContext> {

    private static final long serialVersionUID = 1819548808605962648L;

    private static final ThreadLocal<IdentityUtilContext> IDENTITYUTILCONTEXT = ThreadLocalManager.createThreadLocal(IdentityUtilContext.class);

    protected Site identifier;
    
    public static IdentityUtilContext getUtilContext() {
        IdentityUtilContext anyIdentityUtilContext = IDENTITYUTILCONTEXT.get();
        if (anyIdentityUtilContext != null) {
            return anyIdentityUtilContext.peek();
        }
        return anyIdentityUtilContext;
    }

    public static void setUtilContext(IdentityUtilContext identityUtilContext) {
        IdentityUtilContext anyIdentityUtilContext = IDENTITYUTILCONTEXT.get();
        if (anyIdentityUtilContext != null) {
            if (identityUtilContext == null) {
                anyIdentityUtilContext.pop();
                return;
            } else {
                anyIdentityUtilContext.push(identityUtilContext);
                return;
            }
        }
        if (identityUtilContext == null) {
            ThreadLocalManager.remove(IDENTITYUTILCONTEXT);
        } else {
            identityUtilContext.push(identityUtilContext);
            IDENTITYUTILCONTEXT.set(identityUtilContext);
        }
    }

    public Site getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Site identifier) {
        this.identifier = identifier;
    }
}

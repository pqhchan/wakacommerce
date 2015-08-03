package com.wakacommerce.common.rule;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mvel2.CompileException;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;

import com.wakacommerce.common.util.EfficientLRUMap;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractRuleProcessor<T> implements RuleProcessor<T> {
    
    protected final Log LOG = LogFactory.getLog(this.getClass());

    protected Map<String, Serializable> expressionCache = new EfficientLRUMap<String, Serializable>(1000);
    protected ParserContext parserContext;
    protected Map<String, String> contextClassNames = new HashMap<String, String> ();

    protected ParserContext getParserContext() {
        if (parserContext == null) {
            parserContext = new ParserContext();
            parserContext.addImport("MVEL", MVEL.class);
            parserContext.addImport("MvelHelper", MvelHelper.class);
        }
        return parserContext;
    }

    protected Boolean executeExpression(String expression, Map<String, Object> vars) {
        Serializable exp = (Serializable) expressionCache.get(expression);
        vars.put("MVEL", MVEL.class);

        if (exp == null) {
            try {
                exp = MVEL.compileExpression(expression, getParserContext());
            } catch (CompileException ce) {
                LOG.warn("Compile exception processing phrase: " + expression, ce);
                return Boolean.FALSE;
            }
            expressionCache.put(expression, exp);
        }

        try {
            return (Boolean) MVEL.executeExpression(exp, vars);
        } catch (Exception e) {
            LOG.error(e);
        }

        return false;
    }

    public Map<String, String> getContextClassNames() {
        return contextClassNames;
    }

    public void setContextClassNames(Map<String, String> contextClassNames) {
        this.contextClassNames = contextClassNames;
    }

}

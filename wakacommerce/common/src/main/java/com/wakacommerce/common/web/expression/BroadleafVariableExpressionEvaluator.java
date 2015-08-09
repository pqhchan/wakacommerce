package com.wakacommerce.common.web.expression;

import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.spring4.expression.SpelVariableExpressionEvaluator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
public class BroadleafVariableExpressionEvaluator extends SpelVariableExpressionEvaluator {
    
    @Resource(name = "blVariableExpressions")
    protected List<BroadleafVariableExpression> expressions = new ArrayList<BroadleafVariableExpression>();
    
    @Override
    protected Map<String,Object> computeAdditionalExpressionObjects(final IProcessingContext processingContext) {
        Map<String, Object> map = new HashMap<String, Object>();
        
        for (BroadleafVariableExpression expression : expressions) {
            if (!(expression instanceof NullBroadleafVariableExpression)) {
                map.put(expression.getName(), expression);
            }
        }
        
        return map;
    }

}

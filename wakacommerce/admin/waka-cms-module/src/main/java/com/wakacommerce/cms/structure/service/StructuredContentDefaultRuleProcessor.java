package com.wakacommerce.cms.structure.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.rule.AbstractRuleProcessor;
import com.wakacommerce.common.structure.dto.StructuredContentDTO;

import java.util.Map;

@Service("blContentDefaultRuleProcessor")
public class StructuredContentDefaultRuleProcessor extends AbstractRuleProcessor<StructuredContentDTO> {
    private static final Log LOG = LogFactory.getLog(StructuredContentDefaultRuleProcessor.class);

    public boolean checkForMatch(StructuredContentDTO sc, Map<String, Object> vars) {
        String ruleExpression = sc.getRuleExpression();

        if (ruleExpression != null) {
            if (LOG.isTraceEnabled())  {
                LOG.trace("Processing content rule for StructuredContent with id " + sc.getId() +".   Value = " + ruleExpression);
            }
            boolean result = executeExpression(ruleExpression, vars);
            if (! result) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Content failed to pass rule and will not be included for StructuredContent with id " + sc.getId() +".   Value = " + ruleExpression);
                }
            }

            return result;
        } else {
            return true;
        }
    }
}

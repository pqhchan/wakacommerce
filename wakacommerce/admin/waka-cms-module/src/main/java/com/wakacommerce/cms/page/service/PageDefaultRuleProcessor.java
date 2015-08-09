 
package com.wakacommerce.cms.page.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.wakacommerce.common.page.dto.PageDTO;
import com.wakacommerce.common.rule.AbstractRuleProcessor;

import java.util.Map;

/**
 *
 * @ hui
 */
@Service("blPageDefaultRuleProcessor")
public class PageDefaultRuleProcessor extends AbstractRuleProcessor<PageDTO> {
    private static final Log LOG = LogFactory.getLog(PageDefaultRuleProcessor.class);

    public boolean checkForMatch(PageDTO page, Map<String, Object> vars) {
        String ruleExpression = page.getRuleExpression();

        if (ruleExpression != null) {
            if (LOG.isTraceEnabled())  {
                LOG.trace("Processing content rule for page with id " + page.getId() +".   Value = " + ruleExpression);
            }
            boolean result = executeExpression(ruleExpression, vars);
            if (! result) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("Page failed to pass rule and will not be included for Page with id " + page.getId() +".   Value = " + ruleExpression);
                }
            }

            return result;
        } else {
            // If no rule found, then consider this a match.
            return true;
        }
    }
}


package com.wakacommerce.common.security.service;

import org.owasp.validator.html.CleanResults;

import com.wakacommerce.common.exception.ServiceException;

/**
 * 
 */
public class CleanStringException extends ServiceException {

    public CleanStringException(CleanResults cleanResults) {
        this.cleanResults = cleanResults;
    }

    protected CleanResults cleanResults;

    public CleanResults getCleanResults() {
        return cleanResults;
    }

    public void setCleanResults(CleanResults cleanResults) {
        this.cleanResults = cleanResults;
    }
}

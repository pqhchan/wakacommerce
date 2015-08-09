
package com.wakacommerce.common.extensibility.context.merge.handlers;

/**
 *
 * @ hui
 */
public class CommaDelimitedNodeValueMerge extends NodeValueMerge {

    @Override
    public String getDelimiter() {
        return ",";
    }

    @Override
    public String getRegEx() {
        return getDelimiter();
    }
}

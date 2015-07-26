
package com.wakacommerce.common.extensibility.context.merge.handlers;

/**
 *Jeff Fischer
 */
public class SpaceDelimitedNodeValueMerge extends NodeValueMerge {

    @Override
    public String getDelimiter() {
        return " ";
    }
}

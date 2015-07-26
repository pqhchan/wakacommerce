package com.wakacommerce.common.extensibility.context.merge.handlers;

public class SpaceDelimitedNodeValueMerge extends NodeValueMerge {

    @Override
    public String getDelimiter() {
        return " ";
    }
}

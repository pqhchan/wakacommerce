
package com.wakacommerce.common.extensibility.context.merge.handlers;

import org.w3c.dom.Node;

import java.util.List;

/**
 *
 * @ hui
 */
public class MergeHandlerAdapter implements MergeHandler {

    public MergeHandler[] getChildren() {
        return null;
    }

    public String getName() {
        return null;
    }

    public int getPriority() {
        return 0;
    }

    public String getXPath() {
        return null;
    }

    public Node[] merge(List<Node> nodeList1, List<Node> nodeList2,
            List<Node> exhaustedNodes) {
        return null;
    }

    public void setChildren(MergeHandler[] children) {
        //do nothing
    }

    public void setName(String name) {
        //do nothing
    }

    public void setPriority(int priority) {
        //do nothing
    }

    public void setXPath(String xpath) {
        //do nothing
    }

}

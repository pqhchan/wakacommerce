
package com.wakacommerce.common.extensibility.context.merge.handlers;

import org.w3c.dom.Node;

import java.util.List;

/**
 *
 * @ hui
 */
public class NodeReplace extends NodeReplaceInsert {

    @Override
    protected boolean checkNode(List<Node> usedNodes, Node[] primaryNodes, Node node) {
        if (replaceNode(primaryNodes, node, usedNodes)) {
            return true;
        }
        //check if this same node already exists
        if (exactNodeExists(primaryNodes, node, usedNodes)) {
            return true;
        }
        return false;
    }

    protected boolean replaceNode(Node[] primaryNodes, Node testNode, List<Node> usedNodes) {
        boolean foundItem = false;
        for (int j=0;j<primaryNodes.length;j++){
            if (primaryNodes[j].getNodeName().equals(testNode.getNodeName())) {
                Node newNode = primaryNodes[j].getOwnerDocument().importNode(testNode.cloneNode(true), true);
                primaryNodes[j].getParentNode().replaceChild(newNode, primaryNodes[j]);
                usedNodes.add(testNode);
                foundItem = true;
            }
        }

        return foundItem;
    }
}

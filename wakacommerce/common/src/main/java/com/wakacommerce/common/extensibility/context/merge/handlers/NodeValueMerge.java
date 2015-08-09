
package com.wakacommerce.common.extensibility.context.merge.handlers;

import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @ hui
 */
public class NodeValueMerge extends BaseHandler {

    protected String delimiter = " ";
    protected String regex = "[\\s\\n\\r]+";

    @Override
    public Node[] merge(List<Node> nodeList1, List<Node> nodeList2, List<Node> exhaustedNodes) {
        if (CollectionUtils.isEmpty(nodeList1) || CollectionUtils.isEmpty(nodeList2)) {
            return null;
        }
        Node node1 = nodeList1.get(0);
        Node node2 = nodeList2.get(0);
        Set<String> finalItems = getMergedNodeValues(node1, node2);
        StringBuilder sb = new StringBuilder();
        Iterator<String> itr = finalItems.iterator();
        while (itr.hasNext()) {
            sb.append(itr.next());
            if (itr.hasNext()) {
                sb.append(getDelimiter());
            }
        }
        node1.setNodeValue(sb.toString());
        node2.setNodeValue(sb.toString());

        Node[] response = new Node[nodeList2.size()];
        for (int j=0;j<response.length;j++){
            response[j] = nodeList2.get(j);
        }
        return response;
    }

    protected Set<String> getMergedNodeValues(Node node1, Node node2) {
        String[] items1 = node1.getNodeValue().split(getRegEx());
        String[] items2 = node2.getNodeValue().split(getRegEx());
        Set<String> finalItems = new LinkedHashSet<String>();
        for (String anItems1 : items1) {
            finalItems.add(anItems1.trim());
        }
        for (String anItems2 : items2) {
            finalItems.add(anItems2.trim());
        }
        return finalItems;
    }

    public String getDelimiter() {
        return delimiter;
    }

    public String getRegEx() {
        return regex;
    }
}

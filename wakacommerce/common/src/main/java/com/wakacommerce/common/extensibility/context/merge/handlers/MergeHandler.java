package com.wakacommerce.common.extensibility.context.merge.handlers;

import org.w3c.dom.Node;

import java.util.List;

public interface MergeHandler {
    
    /**
     * Perform the merge using the supplied list of nodes from the source and
     * patch documents, respectively. Also, a list of nodes that have already
     * been merged is provided and may be used by the implementation when
     * necessary.
     * 
     * @param nodeList1 list of nodes to be merged from the source document
     * @param nodeList2 list of nodes to be merged form the patch document
     * @param exhaustedNodes already merged nodes
     * @return list of merged nodes
     */
    public Node[] merge(List<Node> nodeList1, List<Node> nodeList2, List<Node> exhaustedNodes);
    
    public int getPriority();
    
    public void setPriority(int priority);
    
    public String getXPath();

    public void setXPath(String xpath);
    
    public MergeHandler[] getChildren();
    
    public void setChildren(MergeHandler[] children);
    
    /**
     * Retrieve the name associated with this merge handlers. Merge handler names are
     * period-delimited numeric strings that define the hierarchical relationship of mergehandlers
     * and their children. For example, "2" could be used to define the second handler in the configuration
     * list and "2.1" would be the name describing the first child handler of "2".
     * 
     * @return the period-delimited numeric string that names this handler
     */
    public String getName();
    
    public void setName(String name);

}

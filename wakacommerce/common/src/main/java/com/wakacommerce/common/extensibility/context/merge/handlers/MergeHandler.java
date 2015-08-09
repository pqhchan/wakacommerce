package com.wakacommerce.common.extensibility.context.merge.handlers;

import org.w3c.dom.Node;

import java.util.List;

public interface MergeHandler {

    public Node[] merge(List<Node> nodeList1, List<Node> nodeList2, List<Node> exhaustedNodes);
    
    public int getPriority();
    
    public void setPriority(int priority);
    
    public String getXPath();

    public void setXPath(String xpath);
    
    public MergeHandler[] getChildren();
    
    public void setChildren(MergeHandler[] children);

    public String getName();
    
    public void setName(String name);

}


package com.wakacommerce.common.extensibility.context.merge;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.wakacommerce.common.extensibility.context.merge.handlers.MergeHandler;

import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @ hui
 */
public class MergePoint {
    
    private static final Log LOG = LogFactory.getLog(MergePoint.class);
    
    private MergeHandler handler;
    private Document doc1;
    private Document doc2;
    private XPath xPath;
    
    public MergePoint(MergeHandler handler, Document doc1, Document doc2) {
        this.handler = handler;
        this.doc1 = doc1;
        this.doc2 = doc2;
        XPathFactory factory=XPathFactory.newInstance();
        xPath=factory.newXPath();
    }

    public Node[] merge(List<Node> exhaustedNodes) throws XPathExpressionException, TransformerException {
        return merge(handler, exhaustedNodes);
    }
    
    private Node[] merge(MergeHandler handler, List<Node> exhaustedNodes) throws XPathExpressionException, TransformerException {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Processing handler: " + handler.getXPath());
        }
        if (handler.getChildren() != null) {
            MergeHandler[] children = handler.getChildren();
            for (MergeHandler aChildren : children) {
                Node[] temp = merge(aChildren, exhaustedNodes);
                if (temp != null) {
                    Collections.addAll(exhaustedNodes, temp);
                }
            }
        }
        String[] xPaths = handler.getXPath().split(" ");
        List<Node> nodeList1 = new ArrayList<Node>();
        List<Node> nodeList2 = new ArrayList<Node>();
        for (String xPathVal : xPaths) {
            NodeList temp1 = (NodeList) xPath.evaluate(xPathVal, doc1, XPathConstants.NODESET);
            if (temp1 != null) {
                int length = temp1.getLength();
                for (int j=0;j<length;j++) {
                    nodeList1.add(temp1.item(j));
                }
            }
            NodeList temp2 = (NodeList) xPath.evaluate(xPathVal, doc2, XPathConstants.NODESET);
            if (temp2 != null) {
                int length = temp2.getLength();
                for (int j=0;j<length;j++) {
                    nodeList2.add(temp2.item(j));
                }
            }
        }
        if (nodeList1 != null && nodeList2 != null) {
            return handler.merge(nodeList1, nodeList2, exhaustedNodes);
        }
        return null;
    }
}

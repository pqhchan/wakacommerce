
package com.wakacommerce.common.extensibility.context.merge.handlers;

import org.w3c.dom.Node;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @ hui
 */
public class SchemaLocationNodeValueMerge extends SpaceDelimitedNodeValueMerge {

    @Override
    protected Set<String> getMergedNodeValues(Node node1, Node node2) {
        String node1Values = getSanitizedValue(node1.getNodeValue());
        String node2Values = getSanitizedValue(node2.getNodeValue());
        
        Set<String> finalItems = new LinkedHashSet<String>();
        for (String node1Value : node1Values.split(getRegEx())) {
            finalItems.add(node1Value.trim());
        }
        for (String node2Value : node2Values.split(getRegEx())) {
            // Only add in this new attribute value if we haven't seen it yet
            if (!finalItems.contains(node2Value.trim())) {
                finalItems.add(node2Value.trim());
            }
        }
        return finalItems;
    }

    protected String getSanitizedValue(String attributeValue) {
        Pattern springVersionPattern = Pattern.compile("(spring-\\w*-[0-9]\\.[0-9]\\.xsd)");
        Matcher versionMatcher = springVersionPattern.matcher(attributeValue);
        while (versionMatcher.find()) {
            String match = versionMatcher.group();
            String replacement = match.replaceAll("-[0-9]\\.[0-9]", "");
            attributeValue = attributeValue.replaceAll(match, replacement);
        }
        return attributeValue;
    }
    
}

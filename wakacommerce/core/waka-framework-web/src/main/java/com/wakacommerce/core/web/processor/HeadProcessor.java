
package com.wakacommerce.core.web.processor;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.Node;
import org.thymeleaf.exceptions.TemplateProcessingException;
import org.thymeleaf.processor.element.AbstractFragmentHandlingElementProcessor;
import org.thymeleaf.standard.expression.Expression;
import org.thymeleaf.standard.expression.StandardExpressions;
import org.thymeleaf.standard.processor.attr.StandardFragmentAttrProcessor;

import com.wakacommerce.core.web.processor.extension.HeadProcessorExtensionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 *
 * @ hui
 */
@Deprecated
public class HeadProcessor extends AbstractFragmentHandlingElementProcessor {

    @Resource(name = "blHeadProcessorExtensionManager")
    protected HeadProcessorExtensionListener extensionManager;

    public static final String FRAGMENT_ATTR_NAME = StandardFragmentAttrProcessor.ATTR_NAME;
    protected String HEAD_PARTIAL_PATH = "layout/partials/head";

    public HeadProcessor() {
        super("head");
    }

    @Override
    public int getPrecedence() {
        return 10000;
    }

    @Override
    protected boolean getRemoveHostNode(final Arguments arguments, final Element element) {
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected List<Node> computeFragment(final Arguments arguments, final Element element) {
        // The pageTitle attribute could be an expression that needs to be evaluated. Try to evaluate, but fall back
        // to its text value if the expression wasn't able to be processed. This will allow things like
        // pageTitle="Hello this is a string"
        // as well as expressions like
        // pageTitle="${'Hello this is a ' + product.name}"
        
        String pageTitle = element.getAttributeValue("pageTitle");
        try {
            Expression expression = (Expression) StandardExpressions.getExpressionParser(arguments.getConfiguration())
                    .parseExpression(arguments.getConfiguration(), arguments, pageTitle);
            pageTitle = (String) expression.execute(arguments.getConfiguration(), arguments);
        } catch (TemplateProcessingException e) {
            // Do nothing.
        }
        ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put("pageTitle", pageTitle);
        ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put("additionalCss", element.getAttributeValue("additionalCss"));

        extensionManager.processAttributeValues(arguments, element);
        
        //the commit at https://github.com/thymeleaf/thymeleaf/commit/b214d9b5660369c41538e023d4b8d7223ebcbc22 along with
        //the referenced issue at https://github.com/thymeleaf/thymeleaf/issues/205
        
        
//        return new FragmentAndTarget(HEAD_PARTIAL_PATH, WholeFragmentSpec.INSTANCE);
        return new ArrayList<Node>();
    }

}

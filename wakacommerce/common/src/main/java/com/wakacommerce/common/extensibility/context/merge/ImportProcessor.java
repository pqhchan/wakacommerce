package com.wakacommerce.common.extensibility.context.merge;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.wakacommerce.common.extensibility.context.ResourceInputStream;
import com.wakacommerce.common.extensibility.context.merge.exceptions.MergeException;

/**
 * <p>该类用来用来解析spring配置文件中的import元素。
 * <p>程序会动态的将import元素对应的资源加载进来，并且置于当前资源之后。同时，将该元素从源文件中删除。
 */
public class ImportProcessor {

    private static final Log LOG = LogFactory.getLog(ImportProcessor.class);
    private static final String IMPORT_PATH = "/beans/import";

    protected ResourceLoader loader;
    protected DocumentBuilder builder;
    protected XPath xPath;

    public ImportProcessor(ResourceLoader loader) {
        this.loader = loader;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            builder = dbf.newDocumentBuilder();
            XPathFactory factory = XPathFactory.newInstance();
            xPath = factory.newXPath();
        } catch (ParserConfigurationException e) {
            LOG.error("Unable to create document builder", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 遍历sources资源数组，检查配置文件中有没有包含import元素。如果有的话，程序会将该import元素对应的
     * 资源加载进来并置于资源数组当前source的后面。同时，将该import元素从source的源文件中删除
     * 
     * @param sources
     * @return
     * @throws MergeException
     */
    public ResourceInputStream[] extract(ResourceInputStream[] sources) throws MergeException {
        if (sources == null) {
            return null;
        }
        try {
            DynamicResourceIterator resourceList = new DynamicResourceIterator();
            resourceList.addAll(Arrays.asList(sources));
            while(resourceList.hasNext()) {
                ResourceInputStream myStream = resourceList.nextResource();
                Document doc = builder.parse(myStream);
                NodeList nodeList = (NodeList) xPath.evaluate(IMPORT_PATH, doc, XPathConstants.NODESET);
                int length = nodeList.getLength();
                for (int j=0;j<length;j++) {
                    Element element = (Element) nodeList.item(j);
                    Resource resource = loader.getResource(element.getAttribute("resource"));
                    ResourceInputStream ris = new ResourceInputStream(resource.getInputStream(), resource.getURL().toString());
                    resourceList.addEmbeddedResource(ris);
                    element.getParentNode().removeChild(element);
                }
                if (length > 0) {
                    TransformerFactory tFactory = TransformerFactory.newInstance();
                    Transformer xmlTransformer = tFactory.newTransformer();
                    xmlTransformer.setOutputProperty(OutputKeys.VERSION, "1.0");
                    xmlTransformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                    xmlTransformer.setOutputProperty(OutputKeys.METHOD, "xml");
                    xmlTransformer.setOutputProperty(OutputKeys.INDENT, "yes");

                    DOMSource source = new DOMSource(doc);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(baos));
                    StreamResult result = new StreamResult(writer);
                    xmlTransformer.transform(source, result);

                    byte[] itemArray = baos.toByteArray();

                    resourceList.set(resourceList.getPosition() - 1, new ResourceInputStream(new ByteArrayInputStream(itemArray), null, myStream.getNames()));
                } else {
                    myStream.reset();
                }
            }

            return resourceList.toArray(new ResourceInputStream[resourceList.size()]);
        } catch (Exception e) {
            throw new MergeException(e);
        }
    }
}

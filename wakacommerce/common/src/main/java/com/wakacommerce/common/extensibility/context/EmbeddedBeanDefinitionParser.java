package com.wakacommerce.common.extensibility.context;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.wakacommerce.common.exception.ExceptionHelper;
import com.wakacommerce.common.extensibility.context.merge.ImportProcessor;
import com.wakacommerce.common.extensibility.context.merge.exceptions.MergeException;

public class EmbeddedBeanDefinitionParser extends AbstractBeanDefinitionParser {

    private static Log LOG = LogFactory.getLog(EmbeddedBeanDefinitionParser.class);

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        try {
            String[] broadleafConfigLocations = StandardConfigLocations.retrieveAll(StandardConfigLocations.APPCONTEXTTYPE);

            ArrayList<ResourceInputStream> sources = new ArrayList<ResourceInputStream>(20);
            for (String location : broadleafConfigLocations) {
                InputStream source = getClass().getClassLoader().getResourceAsStream(location);
                if (source != null) {
                    sources.add(new ResourceInputStream(source, location));
                }
            }
            ResourceInputStream[] filteredSources = new ResourceInputStream[]{};
            filteredSources = sources.toArray(filteredSources);

            List<Resource> locations = new ArrayList<Resource>();
            List<Element> overrideItemElements = DomUtils.getChildElementsByTagName(element, "location");
            for (Element overrideItem : overrideItemElements) {
                String location = overrideItem.getAttribute("value");
                Resource[] resources = ((ResourcePatternResolver) parserContext.getReaderContext().getResourceLoader()).getResources(location);
                if (ArrayUtils.isEmpty(resources)) {
                    LOG.warn("Unable to find the resource: " + location);
                } else {
                    locations.add(resources[0]);
                }
            }

            ResourceInputStream[] patches = new ResourceInputStream[locations.size()];
            for (int i = 0; i < locations.size(); i++) {
                patches[i] = new ResourceInputStream(locations.get(i).getInputStream(), locations.get(i).getDescription());

                if (patches[i] == null || patches[i].available() <= 0) {
                    throw new IOException("Unable to open an input stream on specified application context resource: " + locations.get(i).getDescription());
                }
            }

            ImportProcessor importProcessor = new ImportProcessor(parserContext.getReaderContext().getResourceLoader());
            try {
                filteredSources = importProcessor.extract(filteredSources);
                patches = importProcessor.extract(patches);
            } catch (MergeException e) {
                throw new FatalBeanException("Unable to merge source and patch locations", e);
            }

            Resource[] resources = new MergeApplicationContextXmlConfigResource().getConfigResources(filteredSources, patches);
            parserContext.getReaderContext().getReader().loadBeanDefinitions(resources[0]);
            return null;
        } catch (IOException e) {
            throw ExceptionHelper.refineException(e);
        }
    }
}

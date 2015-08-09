
package com.wakacommerce.common.extensibility.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;

import com.wakacommerce.common.extensibility.context.merge.ImportProcessor;
import com.wakacommerce.common.extensibility.context.merge.exceptions.MergeException;

/**
 *
 * @ hui
 */
public class MergeClassPathXMLApplicationContext extends AbstractMergeXMLApplicationContext {

    public MergeClassPathXMLApplicationContext(ApplicationContext parent) {
        super(parent);
    }

    public MergeClassPathXMLApplicationContext(String[] sourceLocations, String[] patchLocations) throws BeansException {
        this(sourceLocations, patchLocations, null);
    }

    public MergeClassPathXMLApplicationContext(String[] sourceLocations, String[] patchLocations, ApplicationContext parent) throws BeansException {
        this(parent);
        
        ResourceInputStream[] sources = new ResourceInputStream[sourceLocations.length];
        for (int j=0;j<sourceLocations.length;j++){
            sources[j] = new ResourceInputStream(getClassLoader(parent).getResourceAsStream(sourceLocations[j]), sourceLocations[j]);
        }
        
        ResourceInputStream[] patches = new ResourceInputStream[patchLocations.length];
        for (int j=0;j<patches.length;j++){
            patches[j] = new ResourceInputStream(getClassLoader(parent).getResourceAsStream(patchLocations[j]), patchLocations[j]);
        }

        ImportProcessor importProcessor = new ImportProcessor(this);
        try {
            sources = importProcessor.extract(sources);
            patches = importProcessor.extract(patches);
        } catch (MergeException e) {
            throw new FatalBeanException("Unable to merge source and patch locations", e);
        }

        this.configResources = new MergeApplicationContextXmlConfigResource().getConfigResources(sources, patches);
        refresh();
    }

    protected ClassLoader getClassLoader(ApplicationContext parent) {
        return MergeClassPathXMLApplicationContext.class.getClassLoader();
    }
    
}

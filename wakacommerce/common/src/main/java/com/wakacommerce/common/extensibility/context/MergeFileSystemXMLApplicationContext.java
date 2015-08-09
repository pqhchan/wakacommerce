
package com.wakacommerce.common.extensibility.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.context.ApplicationContext;

import com.wakacommerce.common.extensibility.context.merge.ImportProcessor;
import com.wakacommerce.common.extensibility.context.merge.exceptions.MergeException;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 *
 * @ hui
 */
public class MergeFileSystemXMLApplicationContext extends AbstractMergeXMLApplicationContext {

    public MergeFileSystemXMLApplicationContext(ApplicationContext parent) {
        super(parent);
    }

    public MergeFileSystemXMLApplicationContext(String[] sourceLocations, String[] patchLocations) throws BeansException {
        this(sourceLocations, patchLocations, null);
    }

    public MergeFileSystemXMLApplicationContext(String[] sourceLocations, String[] patchLocations, ApplicationContext parent) throws BeansException {
        this(parent);
        
        ResourceInputStream[] sources;
        ResourceInputStream[] patches;
        try {
            sources = new ResourceInputStream[sourceLocations.length];
            for (int j=0;j<sourceLocations.length;j++){
                File temp = new File(sourceLocations[j]);
                sources[j] = new ResourceInputStream(new BufferedInputStream(new FileInputStream(temp)), sourceLocations[j]);
            }
            
            patches = new ResourceInputStream[patchLocations.length];
            for (int j=0;j<patches.length;j++){
                File temp = new File(patchLocations[j]);
                sources[j] = new ResourceInputStream(new BufferedInputStream(new FileInputStream(temp)), patchLocations[j]);
            }
        } catch (FileNotFoundException e) {
            throw new FatalBeanException("Unable to merge context files", e);
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

}

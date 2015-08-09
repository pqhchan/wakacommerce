
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
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @ hui
 */
public class MergeFileSystemAndClassPathXMLApplicationContext extends AbstractMergeXMLApplicationContext {

    public MergeFileSystemAndClassPathXMLApplicationContext(ApplicationContext parent) {
        super(parent);
    }
    
    public MergeFileSystemAndClassPathXMLApplicationContext(String[] classPathLocations, String[] fileSystemLocations) throws BeansException {
        this(classPathLocations, fileSystemLocations, null);
    }

    public MergeFileSystemAndClassPathXMLApplicationContext(LinkedHashMap<String, ResourceType> locations, ApplicationContext parent) throws BeansException {
        this(parent);

        ResourceInputStream[] resources = new ResourceInputStream[locations.size()];
        int j = 0;
        for (Map.Entry<String, ResourceType> entry : locations.entrySet()) {
            switch (entry.getValue()) {
                case CLASSPATH:
                    resources[j] = new ResourceInputStream(getClassLoader(parent).getResourceAsStream(entry.getKey()), entry.getKey());
                    break;
                case FILESYSTEM:
                    try {
                        File temp = new File(entry.getKey());
                        resources[j] = new ResourceInputStream(new BufferedInputStream(new FileInputStream(temp)), entry.getKey());
                    } catch (FileNotFoundException e) {
                        throw new FatalBeanException("Unable to merge context files", e);
                    }
                    break;
            }
            j++;
        }

        ImportProcessor importProcessor = new ImportProcessor(this);
        try {
            resources = importProcessor.extract(resources);
        } catch (MergeException e) {
            throw new FatalBeanException("Unable to merge source and patch locations", e);
        }

        this.configResources = new MergeApplicationContextXmlConfigResource().getConfigResources(resources, null);
        refresh();
    }

    public MergeFileSystemAndClassPathXMLApplicationContext(String[] classPathLocations, String[] fileSystemLocations, ApplicationContext parent) throws BeansException {
        this(parent);

        ResourceInputStream[] classPathSources;
        ResourceInputStream[] fileSystemSources;
        try {
            classPathSources = new ResourceInputStream[classPathLocations.length];
            for (int j=0;j<classPathLocations.length;j++){
                classPathSources[j] = new ResourceInputStream(getClassLoader(parent).getResourceAsStream(classPathLocations[j]), classPathLocations[j]);
            }

            fileSystemSources = new ResourceInputStream[fileSystemLocations.length];
            for (int j=0;j<fileSystemSources.length;j++){
                File temp = new File(fileSystemLocations[j]);
                fileSystemSources[j] = new ResourceInputStream(new BufferedInputStream(new FileInputStream(temp)), fileSystemLocations[j]);
            }
        } catch (FileNotFoundException e) {
            throw new FatalBeanException("Unable to merge context files", e);
        }

        ImportProcessor importProcessor = new ImportProcessor(this);
        try {
            classPathSources = importProcessor.extract(classPathSources);
            fileSystemSources = importProcessor.extract(fileSystemSources);
        } catch (MergeException e) {
            throw new FatalBeanException("Unable to merge source and patch locations", e);
        }

        this.configResources = new MergeApplicationContextXmlConfigResource().getConfigResources(classPathSources, fileSystemSources);
        refresh();
    }

    protected ClassLoader getClassLoader(ApplicationContext parent) {
        return MergeFileSystemAndClassPathXMLApplicationContext.class.getClassLoader();
    }

    public enum ResourceType {
        FILESYSTEM,CLASSPATH
    }
}

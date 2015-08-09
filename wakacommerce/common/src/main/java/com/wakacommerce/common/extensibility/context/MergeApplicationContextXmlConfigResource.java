package com.wakacommerce.common.extensibility.context;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import com.wakacommerce.common.extensibility.context.merge.MergeXmlConfigResource;
import com.wakacommerce.common.extensibility.context.merge.exceptions.MergeException;
import com.wakacommerce.common.extensibility.context.merge.exceptions.MergeManagerSetupException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MergeApplicationContextXmlConfigResource extends MergeXmlConfigResource {

    private static final Log LOG = LogFactory.getLog(MergeApplicationContextXmlConfigResource.class);

    public Resource[] getConfigResources(ResourceInputStream[] sources, ResourceInputStream[] patches) throws BeansException {
        Resource[] configResources = null;
        ResourceInputStream merged = null;
        try {
            merged = merge(sources);

            if (patches != null) {
                ResourceInputStream[] patches2 = new ResourceInputStream[patches.length+1];
                patches2[0] = merged;
                System.arraycopy(patches, 0, patches2, 1, patches.length);

                merged = merge(patches2);
            }

            //read the final stream into a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            boolean eof = false;
            while (!eof) {
                int temp = merged.read();
                if (temp == -1) {
                    eof = true;
                } else {
                    baos.write(temp);
                }
            }
            configResources = new Resource[]{new ByteArrayResource(baos.toByteArray())};

            if (LOG.isDebugEnabled()) {
                LOG.debug("Merged ApplicationContext Including Patches: \n" + serialize(configResources[0]));
            }
        } catch (MergeException e) {
            throw new FatalBeanException("Unable to merge source and patch locations", e);
        } catch (MergeManagerSetupException e) {
            throw new FatalBeanException("Unable to merge source and patch locations", e);
        } catch (IOException e) {
            throw new FatalBeanException("Unable to merge source and patch locations", e);
        } finally {
            if (merged != null) {
                try{ merged.close(); } catch (Throwable e) {
                    LOG.error("Unable to merge source and patch locations", e);
                }
            }
        }

        return configResources;
    }

}

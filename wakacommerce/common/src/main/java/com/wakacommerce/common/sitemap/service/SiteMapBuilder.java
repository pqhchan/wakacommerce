package com.wakacommerce.common.sitemap.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wakacommerce.common.file.domain.FileWorkArea;
import com.wakacommerce.common.file.service.WakaFileUtils;
import com.wakacommerce.common.sitemap.domain.SiteMapConfiguration;
import com.wakacommerce.common.sitemap.wrapper.SiteMapIndexWrapper;
import com.wakacommerce.common.sitemap.wrapper.SiteMapURLSetWrapper;
import com.wakacommerce.common.sitemap.wrapper.SiteMapURLWrapper;
import com.wakacommerce.common.sitemap.wrapper.SiteMapWrapper;
import com.wakacommerce.common.util.FormatUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

/**
 *
 * @ hui
 */
public class SiteMapBuilder {

    protected static final Log LOG = LogFactory.getLog(SiteMapBuilder.class);
    protected FileWorkArea fileWorkArea;

    protected SiteMapConfiguration siteMapConfig;
    protected SiteMapURLSetWrapper currentURLSetWrapper;
    protected List<String> indexedFileNames = new ArrayList<String>();
    protected String baseUrl;
    protected boolean gzipSiteMapFiles = true;

    public SiteMapBuilder(SiteMapConfiguration siteMapConfig, FileWorkArea fileWorkArea, String baseUrl, boolean gzipSiteMapFiles) {
        this.fileWorkArea = fileWorkArea;
        this.siteMapConfig = siteMapConfig;
        this.currentURLSetWrapper = new SiteMapURLSetWrapper();
        this.baseUrl = baseUrl;
        this.gzipSiteMapFiles = gzipSiteMapFiles;
    }

    public void addUrl(SiteMapURLWrapper urlWrapper) {
        if (currentURLSetWrapper.getSiteMapUrlWrappers().size() >= siteMapConfig.getMaximumUrlEntriesPerFile()) {
            persistIndexedURLSetWrapper(currentURLSetWrapper);
            currentURLSetWrapper = new SiteMapURLSetWrapper();
        }
        currentURLSetWrapper.getSiteMapUrlWrappers().add(urlWrapper);
    }

    protected void persistXMLDocument(String fileName, Object xmlObject) {

        try {
            JAXBContext context = JAXBContext.newInstance(xmlObject.getClass());
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(Marshaller.JAXB_FRAGMENT, true);

            File file = new File(fileWorkArea.getFilePathLocation() + fileName);
            if (LOG.isTraceEnabled()) {
                LOG.trace("Persisting SiteMap document " + file.getAbsolutePath());
            }

            if (!file.exists()) {
                file.createNewFile();
            }
            Writer writer = new BufferedWriter(new FileWriter(file.getAbsoluteFile()));
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            m.marshal(xmlObject, writer);
            writer.close();
        } catch (IOException ioe) {
            LOG.error("IOException occurred persisting XML Document", ioe);
            throw new RuntimeException("Error persisting XML document when trying to build Sitemap", ioe);
        } catch (JAXBException je) {
            LOG.error("JAXBException occurred persisting XML Document", je);
            throw new RuntimeException("Error persisting XML document when trying to build Sitemap", je);
        }
    }

    protected void persistIndexedURLSetWrapper(SiteMapURLSetWrapper urlSetWrapper) {
        String indexedFileName = createNextIndexedFileName();
        indexedFileNames.add(indexedFileName);
        persistXMLDocument(indexedFileName, urlSetWrapper);
    }

    protected void persistNonIndexedSiteMap() {
        indexedFileNames.add(siteMapConfig.getSiteMapFileName());
        persistXMLDocument(siteMapConfig.getSiteMapFileName(), currentURLSetWrapper);
    }

    protected void persistIndexedSiteMap() {
        String now = FormatUtil.formatDateUsingW3C(new Date());
        
        // Save the leftover URL set
        persistIndexedURLSetWrapper(currentURLSetWrapper);

        // Build the siteMapIndex
        SiteMapIndexWrapper siteMapIndexWrapper = new SiteMapIndexWrapper();
        for (String fileName : indexedFileNames) {
            SiteMapWrapper siteMapWrapper = new SiteMapWrapper();
            String fileLoc = null;
            if (gzipSiteMapFiles) {
                fileLoc = WakaFileUtils.appendUnixPaths(baseUrl, fileName + ".gz");
            } else {
                fileLoc = WakaFileUtils.appendUnixPaths(baseUrl, fileName);
            }
            siteMapWrapper.setLoc(fileLoc)   ;         
            siteMapWrapper.setLastmod(now);
            siteMapIndexWrapper.getSiteMapWrappers().add(siteMapWrapper);
        }

        if (LOG.isTraceEnabled()) {
            LOG.trace("Persisting sitemap.xml file for indexed site map ");
        }
        indexedFileNames.add(siteMapConfig.getIndexedSiteMapFileName());
        persistXMLDocument(siteMapConfig.getIndexedSiteMapFileName(),
                siteMapIndexWrapper);
    }

    protected String createNextIndexedFileName() {
        String pattern = siteMapConfig.getSiteMapIndexFilePattern();
        int indexFileNumber = indexedFileNames.size() + 1;
        String fileName = pattern.replaceFirst("###", String.valueOf(indexFileNumber));
        return fileName;
    }

    protected void persistSiteMap() {
        if (indexedFileNames.size() > 0) {
            persistIndexedSiteMap();
        } else {
            persistNonIndexedSiteMap();
        }
    }

    public List<String> getIndexedFileNames() {
        return indexedFileNames;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

}

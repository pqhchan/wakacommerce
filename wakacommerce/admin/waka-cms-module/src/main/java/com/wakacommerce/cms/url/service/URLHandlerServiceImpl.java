package com.wakacommerce.cms.url.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wakacommerce.cms.url.dao.URLHandlerDao;
import com.wakacommerce.cms.url.domain.URLHandler;
import com.wakacommerce.cms.url.domain.URLHandlerDTO;
import com.wakacommerce.common.cache.StatisticsService;
import com.wakacommerce.common.util.EfficientLRUMap;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

@Service("blURLHandlerService")
public class URLHandlerServiceImpl implements URLHandlerService {

    private static final Log LOG = LogFactory.getLog(URLHandlerServiceImpl.class);

    @Resource(name="blURLHandlerDao")
    protected URLHandlerDao urlHandlerDao;

    @Resource(name="blStatisticsService")
    protected StatisticsService statisticsService;

    protected Map<String, Pattern> urlPatternMap = new EfficientLRUMap<String, Pattern>(2000);

    @Override
    public URLHandler findURLHandlerByURI(String uri) {
        return checkForMatches(uri);
    }

    @Override
    public URLHandler findURLHandlerById(Long id) {
        return urlHandlerDao.findURLHandlerById(id);
    }

    @Override
    public List<URLHandler> findAllURLHandlers() {
        return urlHandlerDao.findAllURLHandlers();
    }

    @Override
    @Transactional("blTransactionManager")
    public URLHandler saveURLHandler(URLHandler handler) {
        return urlHandlerDao.saveURLHandler(handler);
    }
    
    protected URLHandler checkForMatches(String requestURI) {
        URLHandler currentHandler = null;
        try {
            List<URLHandler> urlHandlers = findAllURLHandlers();
            for (URLHandler urlHandler : urlHandlers) {
                currentHandler = urlHandler;
                String incomingUrl = currentHandler.getIncomingURL();
                if (!incomingUrl.startsWith("^")) {
                    if (incomingUrl.startsWith("/")) {
                        incomingUrl = "^" + incomingUrl + "$";
                    } else {
                        incomingUrl = "^/" + incomingUrl + "$";
                    }
                }

                Pattern p = urlPatternMap.get(incomingUrl);
                if (p == null) {
                    p = Pattern.compile(incomingUrl);
                    urlPatternMap.put(incomingUrl, p);
                }
                Matcher m = p.matcher(requestURI);
                if (m.find()) {
                    String newUrl = m.replaceFirst(urlHandler.getNewURL());
                    if (newUrl.equals(urlHandler.getNewURL())) {
                        return urlHandler;
                    } else {
                        return new URLHandlerDTO(newUrl, urlHandler.getUrlRedirectType());
                    }
                }

            }
        } catch (RuntimeException re) {
            if (currentHandler != null) {
                // We don't want an invalid regex to cause tons of logging                
                if (LOG.isWarnEnabled()) {
                    LOG.warn("Error parsing URL Handler (incoming =" + currentHandler.getIncomingURL() + "), outgoing = ( "
                            + currentHandler.getNewURL() + "), " + requestURI);
                }
            }
        }


        return null;
    }

}

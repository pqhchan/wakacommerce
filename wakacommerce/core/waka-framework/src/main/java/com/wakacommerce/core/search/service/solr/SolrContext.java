
package com.wakacommerce.core.search.service.solr;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.CloudSolrServer;

/**
 *
 * @ hui
 */
public class SolrContext {

    public static final String PRIMARY = "primary";
    public static final String REINDEX = "reindex";

    protected static SolrServer adminServer = null;
    protected static SolrServer primaryServer = null;
    protected static SolrServer reindexServer = null;

    public static void setPrimaryServer(SolrServer server) {
        if (server != null && CloudSolrServer.class.isAssignableFrom(server.getClass())) {
            CloudSolrServer cs = (CloudSolrServer) server;
            if (StringUtils.isBlank(cs.getDefaultCollection())) {
                cs.setDefaultCollection(PRIMARY);
            }

            if (reindexServer != null) {
                //If we already have a reindex server set, make sure it's not the same instance as the primary
                if (server == reindexServer) {
                    throw new IllegalArgumentException("The primary and reindex CloudSolrServers are the same instances. "
                            + "They must be different instances. Each instance must have a different defaultCollection or "
                            + "the defaultCollection must be unspecified and Broadleaf will set it.");
                }
                
                if (CloudSolrServer.class.isAssignableFrom(reindexServer.getClass())) {
                    //Make sure that the primary and reindex servers are not using the same default collection name
                    if (cs.getDefaultCollection().equals(((CloudSolrServer) reindexServer).getDefaultCollection())) {
                        throw new IllegalStateException("Primary and Reindex servers cannot have the same defaultCollection: "
                                + cs.getDefaultCollection());
                    }
                }
            }
        }

        primaryServer = server;
    }

    public static void setReindexServer(SolrServer server) {
        if (server != null && CloudSolrServer.class.isAssignableFrom(server.getClass())) {
            CloudSolrServer cs = (CloudSolrServer) server;
            if (StringUtils.isBlank(cs.getDefaultCollection())) {
                cs.setDefaultCollection(REINDEX);
            }

            if (primaryServer != null) {
                //If we already have a reindex server set, make sure it's not the same instance as the primary
                if (server == primaryServer) {
                    throw new IllegalArgumentException("The primary and reindex CloudSolrServers are the same instances. "
                            + "They must be different instances. Each instance must have a different defaultCollection or "
                            + "the defaultCollection must be unspecified and Broadleaf will set it.");
                }

                if (CloudSolrServer.class.isAssignableFrom(primaryServer.getClass())) {
                    //Make sure that the primary and reindex servers are not using the same default collection name
                    if (cs.getDefaultCollection().equals(((CloudSolrServer) primaryServer).getDefaultCollection())) {
                        throw new IllegalStateException("Primary and Reindex servers cannot have the same defaultCollection: "
                                + cs.getDefaultCollection());
                    }
                }
            }
        }
        reindexServer = server;
    }

    public static void setAdminServer(SolrServer server) {
        adminServer = server;
    }

    public static SolrServer getAdminServer() {
        if (adminServer != null) {
            return adminServer;
        }
        //If the admin server hasn't been set, return the primary server.
        return getServer();
    }

    public static SolrServer getServer() {
        return primaryServer;
    }

    public static SolrServer getReindexServer() {
        return isSingleCoreMode() ? primaryServer : reindexServer;
    }

    public static boolean isSingleCoreMode() {
        return reindexServer == null;
    }

    public static boolean isSolrCloudMode() {
        return CloudSolrServer.class.isAssignableFrom(getServer().getClass());
    }
}

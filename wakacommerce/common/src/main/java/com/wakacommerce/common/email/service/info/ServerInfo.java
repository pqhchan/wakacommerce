package com.wakacommerce.common.email.service.info;

import java.io.Serializable;

public class ServerInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String serverName;
    private Integer serverPort;
    private Integer securePort;
    private String appName;

    public String getSecureHost() {
        StringBuffer sb = new StringBuffer();
        sb.append(serverName);
        if (!securePort.equals(443)) {
            sb.append(":");
            sb.append(securePort);
        }
        return sb.toString();
    }

    public String getHost() {
        StringBuffer sb = new StringBuffer();
        sb.append(serverName);
        if (!serverPort.equals(80)) {
            sb.append(":");
            sb.append(serverPort);
        }
        return sb.toString();
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public Integer getSecurePort() {
        return securePort;
    }

    public void setSecurePort(Integer securePort) {
        this.securePort = securePort;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}

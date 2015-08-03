package com.wakacommerce.common.config;

public class SystemPropertyRuntimeEnvironmentKeyResolver implements RuntimeEnvironmentKeyResolver {

    protected String environmentKey = "runtime.environment";

    public SystemPropertyRuntimeEnvironmentKeyResolver() {
        // EMPTY
    }

    public String resolveRuntimeEnvironmentKey() {
        return System.getProperty(environmentKey);
    }

    public void setEnvironmentKey(String environmentKey) {
        this.environmentKey = environmentKey;
    }
}

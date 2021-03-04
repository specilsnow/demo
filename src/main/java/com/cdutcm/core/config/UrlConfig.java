package com.cdutcm.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Auther: Mxq
 * @Date: 2018/7/26 16:38
 * @description:
 */
@Component
//@PropertySource(value = {"file:/etc/mamios/ybm_mamios.properties"},ignoreResourceNotFound = true)
@ConfigurationProperties(prefix = "url")
public class UrlConfig {

    private String serverName;
    private String serverPort;

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}

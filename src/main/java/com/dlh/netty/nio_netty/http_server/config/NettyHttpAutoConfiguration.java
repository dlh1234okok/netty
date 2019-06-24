package com.dlh.netty.nio_netty.http_server.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author: dulihong
 * @date: 2019/6/20 16:09
 */
@ConfigurationProperties(prefix = "netty")
@Component
public class NettyHttpAutoConfiguration {

    private String basePackage;

    private String serverPort;

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}

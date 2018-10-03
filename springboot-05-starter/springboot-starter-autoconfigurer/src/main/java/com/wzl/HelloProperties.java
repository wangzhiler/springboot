package com.wzl;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by thinkpad on 2018/10/1.
 */

@ConfigurationProperties(prefix = "com.wzl")
public class HelloProperties {
    private String prefix;
    private String suffix;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}

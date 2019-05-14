package com.im.my.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "servernetty")
public class NettyConfig
{
    private List<Integer> ports;

    public List<Integer> getPorts()
    {
        return ports;
    }

    public void setPorts(List<Integer> ports)
    {
        this.ports = ports;
    }
}

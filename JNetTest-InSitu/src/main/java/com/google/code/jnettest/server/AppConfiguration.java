package com.google.code.jnettest.server;

import org.springframework.stereotype.Component;

@Component
public class AppConfiguration {

    public String getLocalInterfaceName() {
        // TODO
        return "192.168.1.15";
    }
    
    public String getRemoteHostName() {
        // TODO 
        return "127.0.0.1";
    }
    
    public int getLocalRmiPort() {
        // TODO - make configurable
        return 1099;
    }
    
    public int getRemoteRmiPort() {
        // TODO - make configurable
        return 1099;
    }
}

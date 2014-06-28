package com.google.code.jnettest.server.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.code.jnettest.server.app.RmiExporter;
import com.google.code.jnettest.server.app.Server;

@Configuration
@ComponentScan(value={"com.google.code.jnettest.server.app"})
public class DIConfiguration {

//    public Server getServer() {
//        System.out.println("getServer");
//        return new Server();
//    }
//    
//    public RmiExporter getRmiExporter() {
//        System.out.println("getRmiExporter");
//        return new RmiExporter();
//    }
    
}

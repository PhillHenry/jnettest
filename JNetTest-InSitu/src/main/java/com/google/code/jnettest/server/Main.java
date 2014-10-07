package com.google.code.jnettest.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.code.jnettest.server.app.Server;
import com.google.code.jnettest.server.configuration.DIConfiguration;

public class Main {

    public static void main(String[] args) {
        Main app = new Main();
    }
    
    private final AnnotationConfigApplicationContext context;
    
    Main() {
        context = new AnnotationConfigApplicationContext(DIConfiguration.class);
    }
    
    public Server getServer() {
        Server server = context.getBean(Server.class);
        return server;
    }

    public void shutDown() {
        context.close();
    }
}

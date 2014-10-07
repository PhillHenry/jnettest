package com.google.code.jnettest.server;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.google.code.jnettest.server.app.CommandService;
import com.google.code.jnettest.server.app.RmiContext;
import com.google.code.jnettest.server.app.Server;
import com.google.code.jnettest.server.configuration.DIConfiguration;

public class Main {
    
    public static final String CLIENT_PROXY_BEAN_NAME = "clientProxyFactory";

    public static void main(String[] args) {
        Main app = new Main();
    }
    
    private final AnnotationConfigApplicationContext context;
    
    Main() {
        context = new AnnotationConfigApplicationContext(DIConfiguration.class);
    }
    
    Server getServer() {
        Server server = context.getBean(Server.class);
        return server;
    }
    
    CommandService getClient() {
        RmiContext rmiContext = context.getBean(RmiContext.class);
        return rmiContext.getClientProxy();
    }

    public void shutDown() {
        context.close();
    }
}

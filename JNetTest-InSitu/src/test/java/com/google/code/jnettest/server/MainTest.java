package com.google.code.jnettest.server;

import static com.google.code.jnettest.server.app.RmiExporter.SERVICE_NAME;
import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

import com.google.code.jnettest.server.app.CommandService;
import com.google.code.jnettest.server.app.RmiExporter;

public class MainTest {
    
    public static Main main; 
    
    @BeforeClass
    public static void setUp() {
        main = new Main();
    }
    
    @AfterClass
    public static void tearDown() {
        main.shutDown();
    }

    @Test
    public void springAnnotations() {
        assertNotNull(main.getServer());
    }
    
    @Test
    public void makeCall() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/" + SERVICE_NAME);
        rmiProxyFactoryBean.setServiceInterface(CommandService.class);
        rmiProxyFactoryBean.afterPropertiesSet();
        Object proxy = rmiProxyFactoryBean.getObject();
        assertNotNull(proxy);
    }

}

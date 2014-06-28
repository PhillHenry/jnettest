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
import com.google.code.jnettest.server.commands.Command;

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
        CommandService proxy = getCommandService();
        DummyCommand command = new DummyCommand();
        DummyCommand returned = (DummyCommand) proxy.execute(command);
        assertEquals(0, command.getCallCount());
        assertEquals(1, returned.getCallCount());
    }

    private CommandService getCommandService() {
        RmiProxyFactoryBean rmiProxyFactoryBean = new RmiProxyFactoryBean();
        rmiProxyFactoryBean.setServiceUrl("rmi://localhost:1099/" + SERVICE_NAME);
        rmiProxyFactoryBean.setServiceInterface(CommandService.class);
        rmiProxyFactoryBean.afterPropertiesSet();
        CommandService proxy = (CommandService) rmiProxyFactoryBean.getObject();
        assertNotNull(proxy);
        return proxy;
    }

}

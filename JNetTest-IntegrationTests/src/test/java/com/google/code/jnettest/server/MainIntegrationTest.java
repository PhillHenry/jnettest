package com.google.code.jnettest.server;

import static org.junit.Assert.*;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.jnettest.server.app.CommandService;
import com.google.code.jnettest.server.app.Server;
import com.google.code.jnettest.server.commands.Command;

public class MainIntegrationTest {
    
    private static Main app;

    @BeforeClass
    public static void startServer() {
        app = new Main();
    }
    
    @AfterClass
    public static void stopServer() {
        app.shutDown();
    }

    @Test
    public void serverShouldExecuteSimpleCommand() {
        Server server = app.getServer();
        CountingCommand executed = server.execute(new CountingCommand());
        assertEquals(1, executed.getCallCount());
    }
    
    @Test
    public void clientShouldCallServer() {
        CommandService client = app.getClient();
        CountingCommand executed = client.execute(new CountingCommand());
        assertEquals(1, executed.getCallCount());
    }

}

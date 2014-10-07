package com.google.code.jnettest.server;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.jnettest.FreePortFinder;
import com.google.code.jnettest.server.Main;
import com.google.code.jnettest.server.commands.StartTcpServerCommand;
import com.google.code.jnettest.server.commands.jetty.StartTcpServerAction;

public class StartTcpServerCommandIntegrationTest {

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
    public void test() throws IOException {
        int port = new FreePortFinder().getFreePort();
        StartTcpServerCommand command = new StartTcpServerCommand(1, port, 1024);
        app.getClient().execute(command);
    }

}

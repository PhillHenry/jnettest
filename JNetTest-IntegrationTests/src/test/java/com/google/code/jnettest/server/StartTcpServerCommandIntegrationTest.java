package com.google.code.jnettest.server;

import static org.junit.Assert.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.code.jnettest.FreePortFinder;
import com.google.code.jnettest.server.Main;
import com.google.code.jnettest.server.commands.StartTcpServerCommand;
import com.google.code.jnettest.server.commands.jetty.StartTcpClientAction;
import com.google.code.jnettest.server.commands.jetty.StartTcpServerAction;
import com.google.code.jnettest.server.context.Context;
import com.google.code.jnettest.server.results.ResultConditionFactory;
import com.google.code.jnettest.suite.conditions.Condition;

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

    @Test(timeout=2000)
    public void remoteServerShouldRespondToMyClient() throws IOException, InterruptedException {
        int port                = new FreePortFinder().getFreePort();
        int numberOfExchanges   = 1;
        
        executeRemoteCommandToStartServer(port,
                                          numberOfExchanges);
        
        StartTcpClientAction clientAction = startClient(port,
                                                        numberOfExchanges);

        waitForClientInterationToEnd(clientAction);
    }

    private void waitForClientInterationToEnd(StartTcpClientAction clientAction) throws InterruptedException {
        while (!clientAction.isFinished()) {
            Thread.sleep(100);
        }
    }

    private void executeRemoteCommandToStartServer(int port,
                                                   int numberOfExchanges) {
        StartTcpServerCommand   command = new StartTcpServerCommand(numberOfExchanges, port, 1024);
        app.getClient().execute(command);
    }

    private StartTcpClientAction startClient(int port,
                                             int numberOfExchanges)
            throws InterruptedException {
        ResultConditionFactory conditionFactory = new ResultConditionFactory(app.getServer());
        Condition condition = conditionFactory.createCallbackTimerCondition(numberOfExchanges, "client");
        StartTcpClientAction clientAction   = new StartTcpClientAction(condition,
                                                                       port,
                                                                       "localhost",
                                                                       256,
                                                                       1024,
                                                                       1024);
        Channel             clientChannel   = clientAction.execute(app.getSpringContext().getBean(Context.class));
        ChannelFuture       succeededFuture = clientChannel.newSucceededFuture();
        assertTrue(succeededFuture.await(1000));
        return clientAction;
    }

}

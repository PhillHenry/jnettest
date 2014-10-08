package com.google.code.jnettest.suite.jetty;

import static org.junit.Assert.assertTrue;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.code.jnettest.FreePortFinder;
import com.google.code.jnettest.suite.conditions.AtomicCounterCondition;
import com.google.code.jnettest.suite.conditions.Condition;

public abstract class AbstractJettyIntegrationTest {

    private Channel serverChannel;
    private Channel clientChannel;
    protected Condition stillWorking;
    protected JettyClient jettyClient;
    protected JettyServer jettyServer;
    
    protected final JettyConfigurer noOpConfigurer = new JettyConfigurer(null);
    
    @Before
    public void setUp() throws IOException {
        stillWorking = new AtomicCounterCondition(5);
        
        int port = new FreePortFinder().getFreePort();
        
        jettyClient = createJettyClient(port);
        jettyServer = createJettyServer(port);
    }

    protected abstract JettyClient createJettyClient(int port);
    
    protected abstract JettyServer createJettyServer(int port);
    
    @After
    public void teardown() {
        jettyClient.shutdown();
        jettyServer.shutdown();
        
        if (serverChannel != null) serverChannel.close();
        if (clientChannel != null) clientChannel.close();
    }

    @Test(timeout=5000)
    public void test() throws InterruptedException {
        serverChannel = startServer();
        clientChannel = startClient();
        
        waitForCondition();
    }
    
    protected abstract Channel startClient() throws InterruptedException;

    protected abstract Channel startServer() throws InterruptedException;

    private void waitForCondition() throws InterruptedException {
        while (stillWorking.isTrue()) {
            Thread.sleep(100);
        }
    }

    protected Channel waitForSuccess(ChannelFuture succeededFuture)
            throws InterruptedException {
        assertTrue(succeededFuture.await(1000));
        return succeededFuture.channel();
    }

}

package com.google.code.jnettest.suite.jetty;

import static org.junit.Assert.*;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.code.jnettest.suite.conditions.AtomicCounterCondition;
import com.google.code.jnettest.suite.conditions.Condition;

public class JettyTcpIntegrationTest {
    
    private Condition finished;
    private JettyClient jettyClient;
    private JettyServer jettyServer;
    private JettyEchoChannelInitializer<SocketChannel> initializer;
    
    @Before
    public void setUp() {
        finished = new AtomicCounterCondition(5);
        initializer = new JettyEchoChannelInitializer<>(finished);
        int port = 12343; // TODO make configurable
        jettyClient = new JettyClient(port , "localhost");
        jettyServer = new JettyServer(port);
    }
    
    @After
    public void teardown() {
        jettyClient.shutdown();
        jettyServer.shutdown();
    }

    @Test(timeout=5000)
    public void test() throws InterruptedException {
        startServer();
        startClient();
    }

    private Channel startClient() throws InterruptedException {
        Channel channel = jettyClient.start(initializer, NioSocketChannel.class);
        ChannelFuture succeededFuture = channel.newSucceededFuture();
        waitForSuccess(succeededFuture);
        return channel;
    }

    private Channel startServer() throws InterruptedException {
        Channel channel = jettyServer.start(initializer, NioServerSocketChannel.class);
        ChannelFuture succeededFuture = channel.newSucceededFuture();
        waitForSuccess(succeededFuture);
        return channel;
    }

    private void waitForSuccess(ChannelFuture succeededFuture)
            throws InterruptedException {
        assertTrue(succeededFuture.await(1000));
    }

}

package com.google.code.jnettest.suite.jetty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class JettyTcpIntegrationTest extends AbstractJettyIntegrationTest<SocketChannel> {
    
    protected Channel startClient() throws InterruptedException {
        Channel channel = jettyClient.start(initializer, NioSocketChannel.class);
        ChannelFuture succeededFuture = channel.newSucceededFuture();
        return waitForSuccess(succeededFuture);
    }

    protected Channel startServer() throws InterruptedException {
        Channel channel = jettyServer.start(initializer, NioServerSocketChannel.class);
        ChannelFuture succeededFuture = channel.newSucceededFuture();
        return waitForSuccess(succeededFuture);
    }

    @Override
    protected JettyServer createJettyServer(int port) {
        return new JettyServer(port, noOpConfigurer);
    }

    @Override
    protected JettyClient createJettyClient(int port) {
        return new JettyClient(port , "localhost", noOpConfigurer);
    }

}

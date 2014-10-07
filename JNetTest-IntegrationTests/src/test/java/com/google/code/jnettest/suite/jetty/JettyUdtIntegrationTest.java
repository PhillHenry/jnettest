package com.google.code.jnettest.suite.jetty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.nio.NioUdtByteAcceptorChannel;
import io.netty.channel.udt.nio.NioUdtByteConnectorChannel;
import io.netty.channel.udt.nio.NioUdtMessageConnectorChannel;
import io.netty.channel.udt.nio.NioUdtProvider;

public class JettyUdtIntegrationTest extends AbstractJettyIntegrationTest<UdtChannel> {

    @Override
    protected Channel startClient() throws InterruptedException {
        Channel channel = jettyClient.start(initializer, NioUdtByteConnectorChannel.class);
        ChannelFuture succeededFuture = channel.newSucceededFuture();
        return waitForSuccess(succeededFuture);
    }

    @Override
    protected Channel startServer() throws InterruptedException {
        Channel channel = jettyServer.start(initializer, NioUdtByteAcceptorChannel.class);
        ChannelFuture succeededFuture = channel.newSucceededFuture();
        return waitForSuccess(succeededFuture);
    }

    @Override
    protected JettyServer createJettyServer(int port) {
        return new JettyServer(port, NioUdtProvider.MESSAGE_PROVIDER, noOpConfigurer);
    }

    @Override
    protected JettyClient createJettyClient(int port) {
        return new JettyClient(port, "localhost", NioUdtProvider.BYTE_PROVIDER, noOpConfigurer);
    }

}

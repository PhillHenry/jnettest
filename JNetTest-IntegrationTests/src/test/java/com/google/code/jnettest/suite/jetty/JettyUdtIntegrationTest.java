package com.google.code.jnettest.suite.jetty;

import org.junit.Ignore;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.udt.UdtChannel;
import io.netty.channel.udt.nio.NioUdtByteAcceptorChannel;
import io.netty.channel.udt.nio.NioUdtByteConnectorChannel;
import io.netty.channel.udt.nio.NioUdtMessageConnectorChannel;
import io.netty.channel.udt.nio.NioUdtProvider;

public class JettyUdtIntegrationTest extends AbstractJettyIntegrationTest {

    @Override
    protected Channel startClient() throws InterruptedException {
        try {
            JettyEchoChannelInitializer<UdtChannel> echoInitializer 
                = new JettyEchoInitiatingChannelInitializer<>(stillWorking, 256);
//                = new JettyEchoClientHandler(stillWorking, 256);
//                = new ByteEchoClientHandler();
            Channel channel = jettyClient.start(echoInitializer, NioUdtByteConnectorChannel.class);
            ChannelFuture succeededFuture = channel.newSucceededFuture();
            return waitForSuccess(succeededFuture);
        } catch (Exception x) {
            x.printStackTrace();
            return null;
        }
    }

    @Override
    protected Channel startServer() throws InterruptedException {
        JettyEchoChannelInitializer<UdtChannel> initializer = new JettyEchoChannelInitializer<>(stillWorking);
        Channel channel = jettyServer.start(initializer, NioUdtByteAcceptorChannel.class);
        ChannelFuture succeededFuture = channel.newSucceededFuture();
        
        Thread.sleep(1000);
        
        return waitForSuccess(succeededFuture);
    }

    @Override
    protected JettyServer createJettyServer(int port) {
        return new JettyServer(port, NioUdtProvider.BYTE_PROVIDER, noOpConfigurer);
    }

    @Override
    protected JettyClient createJettyClient(int port) {
        return new JettyClient(port, "localhost", NioUdtProvider.BYTE_PROVIDER, noOpConfigurer);
    }

}

package com.google.code.jnettest.suite.jetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;

public class JettyClient {

    private final int            port;
    private final String         host;
    private final EventLoopGroup eventLoopGroup;

    public JettyClient(int port, String host) {
        super();
        this.port       = port;
        this.host       = host;
        eventLoopGroup  = new NioEventLoopGroup();
    }

    public Channel start(
            JettyEchoChannelInitializer<? extends Channel>  echoInitializer, 
            Class<? extends Channel>                        channelFactoryClass) 
                    throws InterruptedException {
        final Bootstrap bootstrap           = new Bootstrap();
        bootstrap.group(eventLoopGroup)
            .channel(channelFactoryClass)
            .handler(echoInitializer);
        final ChannelFuture channelFuture   = bootstrap.connect(host, port).sync();
        return channelFuture.channel();
    }

    public Future<?> shutdown() {
        return eventLoopGroup.shutdownGracefully();
    }

}

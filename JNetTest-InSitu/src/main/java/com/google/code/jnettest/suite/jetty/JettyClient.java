package com.google.code.jnettest.suite.jetty;

import static java.util.concurrent.Executors.newCachedThreadPool;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;

import java.nio.channels.spi.SelectorProvider;

public class JettyClient {

    private final int               port;
    private final String            host;
    private final EventLoopGroup    eventLoopGroup;
    private final JettyConfigurer   configurer;

    public JettyClient(int port, String host, JettyConfigurer configurer) {
        this(port, host, SelectorProvider.provider(), configurer);
    }
    
    public JettyClient(int port, String host, SelectorProvider selectorProvider, JettyConfigurer configurer) {
        super();
        this.port       = port;
        this.host       = host;
        this.configurer = configurer;
        eventLoopGroup  = new NioEventLoopGroup(1, newCachedThreadPool(new MyThreadFactory("client")), selectorProvider);
    }

    public Channel start(
            ChannelHandler              echoInitializer, 
            Class<? extends Channel>    channelFactoryClass) 
                    throws InterruptedException {
        /*
        final Bootstrap boot = new Bootstrap();
        boot.group(eventLoopGroup)
                .channelFactory(NioUdtProvider.BYTE_CONNECTOR)
                .handler(echoInitializer);
        final ChannelFuture channelFuture = boot.connect(host, port).sync();
        return channelFuture.channel();
         */
        final Bootstrap bootstrap           = new Bootstrap();
        bootstrap.group(eventLoopGroup)
            .channel(channelFactoryClass)
            .handler(echoInitializer);
        configurer.configure(bootstrap);
        final ChannelFuture channelFuture   = bootstrap.connect(host, port).sync();
        return channelFuture.channel();
    }

    public Future<?> shutdown() {
        return eventLoopGroup.shutdownGracefully();
    }

}

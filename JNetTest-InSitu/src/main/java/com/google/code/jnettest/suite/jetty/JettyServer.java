package com.google.code.jnettest.suite.jetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;

import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.Executor;

public class JettyServer {

    private final int             port;
    private final ServerBootstrap bootstrap;
    private final EventLoopGroup  acceptGroup;
    private final EventLoopGroup  connectGroup;
    private final JettyConfigurer configurer;

    public JettyServer(int port, JettyConfigurer configurer) {
        this(port, SelectorProvider.provider(), configurer);
    }
    
    public JettyServer(int port, SelectorProvider selectorProvider, JettyConfigurer configurer) {
        super();
        this.port       = port;
        this.configurer = configurer;
        bootstrap       = new ServerBootstrap();
        acceptGroup     = new NioEventLoopGroup(0, (Executor)null, selectorProvider); 
        connectGroup    = new NioEventLoopGroup();
    }

    /**
     * @param channelFactoryClass eg NioServerSocketChannel.class
     */
    public Channel start(
            JettyEchoChannelInitializer<? extends Channel>  echoInitializer, 
            Class<? extends ServerChannel>                  channelFactoryClass) 
                    throws InterruptedException {
        bootstrap.group(acceptGroup, connectGroup)
                .channel(channelFactoryClass)
                .childHandler(echoInitializer);
        configurer.configure(bootstrap);
        return start();
    }

    private Channel start() throws InterruptedException {
        ChannelFuture future = bootstrap.bind(port).sync();
        return future.channel(); //.closeFuture().sync();
    }

    public void shutdown() {
        acceptGroup.shutdownGracefully();
        connectGroup.shutdownGracefully();
    }
}

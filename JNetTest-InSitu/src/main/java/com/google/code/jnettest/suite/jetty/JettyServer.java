package com.google.code.jnettest.suite.jetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class JettyServer {

    private final int             port;
    private final ServerBootstrap bootstrap;
    private final EventLoopGroup  acceptGroup;
    private final EventLoopGroup  connectGroup;

    public JettyServer(int port) {
        super();
        this.port       = port;
        bootstrap       = new ServerBootstrap();
        acceptGroup     = new NioEventLoopGroup();
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

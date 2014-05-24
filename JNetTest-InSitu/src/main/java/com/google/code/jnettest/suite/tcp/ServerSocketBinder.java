package com.google.code.jnettest.suite.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.ServerSocketChannel;

public class ServerSocketBinder {

    private final int backlog;
    private final int port;
    private final String networkInterface;

    public ServerSocketBinder(int backlog, int port, String networkInterface) {
        super();
        this.backlog            = backlog;
        this.port               = port;
        this.networkInterface   = networkInterface;
    }

    protected ServerSocketChannelConfigurer bind() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        SocketAddress       local               = InetSocketAddress.createUnresolved(networkInterface, port);
        serverSocketChannel.bind(local, backlog);
        return new ServerSocketChannelConfigurer(serverSocketChannel);
    }
}

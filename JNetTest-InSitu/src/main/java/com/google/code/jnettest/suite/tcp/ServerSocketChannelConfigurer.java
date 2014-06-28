package com.google.code.jnettest.suite.tcp;

import static java.net.StandardSocketOptions.SO_RCVBUF;

import java.io.IOException;
import java.net.SocketOption;
import java.nio.channels.ServerSocketChannel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerSocketChannelConfigurer {
    
    private static final Logger log = LogManager.getLogger();
    
    private final ServerSocketChannel serverSocketChannel;
    
    public ServerSocketChannelConfigurer(ServerSocketChannel serverSocketChannel) {
        super();
        this.serverSocketChannel = serverSocketChannel;
    }

    public Integer setReceiveBuffer(int receiveBuffer) throws IOException {
        return set(SO_RCVBUF, receiveBuffer);
    }
    
    private <T> T set(SocketOption<T> socketOption, T value) throws IOException {
        serverSocketChannel.setOption(socketOption, value);
        T actual = serverSocketChannel.getOption(socketOption);
        if ((actual == null && value != null) 
                || (value == null && actual != null)
                || !actual.equals(value)) {
            log.warn(String.format("Could not set %s to %s. Actual value is %s", socketOption, value, actual));
        }
        return actual;
    }
    
}

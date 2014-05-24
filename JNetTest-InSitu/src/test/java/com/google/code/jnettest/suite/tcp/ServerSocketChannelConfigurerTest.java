package com.google.code.jnettest.suite.tcp;

import static java.net.StandardSocketOptions.SO_RCVBUF;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.StandardSocketOptions;
import java.nio.channels.ServerSocketChannel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class ServerSocketChannelConfigurerTest {
    
    private ServerSocketChannelConfigurer toTest;
    private ServerSocketChannel mockServerSocketChannel;
    
    @Before
    public void setUp() {
        mockServerSocketChannel = mock(ServerSocketChannel.class);
        toTest = new ServerSocketChannelConfigurer(mockServerSocketChannel);
    }

    @Test
    public void setReceiveBuffer() throws IOException {
        int receiveBuffer = 101;
        toTest.setReceiveBuffer(receiveBuffer);
        verify(mockServerSocketChannel).setOption(SO_RCVBUF, receiveBuffer);
    }

}

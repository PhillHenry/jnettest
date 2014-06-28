package com.google.code.jnettest.suite.tcp;

import static java.net.StandardSocketOptions.SO_RCVBUF;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;

import org.junit.Before;
import org.junit.Test;

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
        Integer receiveBuffer = 101;
        when(mockServerSocketChannel.getOption(SO_RCVBUF)).thenReturn(receiveBuffer);
        Integer actual = toTest.setReceiveBuffer(receiveBuffer);
        verify(mockServerSocketChannel).setOption(SO_RCVBUF, receiveBuffer);
        assertEquals(receiveBuffer, actual);
    }
    
    @Test
    public void setReceiveBufferExceedsMax() throws IOException {
        Integer userRequest = 101;
        Integer max         = userRequest - 1;
        when(mockServerSocketChannel.getOption(SO_RCVBUF)).thenReturn(max);
        Integer actual = toTest.setReceiveBuffer(userRequest);
        verify(mockServerSocketChannel).setOption(SO_RCVBUF, userRequest);
        assertEquals(max, actual);
    }
}

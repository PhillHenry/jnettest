package com.google.code.jnettest.suite.jetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;

import com.google.code.jnettest.suite.conditions.Condition;

public class JettyEchoInitiatingHandlerAdapter
    extends JettyEchoHandlerAdapter {
    
    private final ByteBuf message;

    public JettyEchoInitiatingHandlerAdapter(Condition continueTest, int size) {
        super(continueTest);
        
        message = Unpooled.buffer(size);
        for (int i = 0; i < message.capacity(); i++) {
            message.writeByte((byte) i);
        }
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        ctx.writeAndFlush(message);
    }
}

package com.google.code.jnettest.suite.jetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.google.code.jnettest.suite.conditions.Condition;

public class JettyEchoInitiatingHandlerAdapter
    extends JettyEchoHandlerAdapter //SimpleChannelInboundHandler<ByteBuf> 
{

    private final ByteBuf   message;

//    private final Condition continueTest;

    public JettyEchoInitiatingHandlerAdapter(Condition continueTest,
                                             int size) {
//        this.continueTest = continueTest;
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
/*
    @Override
    public void messageReceived(ChannelHandlerContext ctx,
                                ByteBuf msg) {
        if (continueTest.isTrue()) {
            ctx.write(msg);
        }
        continueTest.event();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx,
                                final Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
    */
}

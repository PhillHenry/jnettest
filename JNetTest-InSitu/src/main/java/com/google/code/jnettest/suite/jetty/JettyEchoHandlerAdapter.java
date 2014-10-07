package com.google.code.jnettest.suite.jetty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jnettest.suite.conditions.Condition;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelHandler.Sharable;

@Sharable
public class JettyEchoHandlerAdapter extends ChannelHandlerAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(JettyEchoHandlerAdapter.class);
    
    private final Condition continueTest;

    public JettyEchoHandlerAdapter(Condition continueTest) {
        super();
        this.continueTest = continueTest;
    }

    @Override
    public void channelRead(final ChannelHandlerContext ctx, Object msg) {
        if (continueTest.isTrue()) {
            ctx.write(msg); 
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush(); 
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx,
            final Throwable cause) {
        logger.error("Could not echo", cause);
        ctx.close();
    }
}

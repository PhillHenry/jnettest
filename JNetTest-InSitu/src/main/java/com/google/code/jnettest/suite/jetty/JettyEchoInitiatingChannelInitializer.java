package com.google.code.jnettest.suite.jetty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

import com.google.code.jnettest.suite.conditions.Condition;

public class JettyEchoInitiatingChannelInitializer<T extends Channel>
    extends JettyEchoChannelInitializer<T> {

    private final int size;

    public JettyEchoInitiatingChannelInitializer(Condition continueTest, int size) {
        super(continueTest);
        this.size = size;
    }

    @Override
    protected ChannelHandler createEchoHandler() {
        return new JettyEchoInitiatingHandlerAdapter(continueTest, size);
    }

}

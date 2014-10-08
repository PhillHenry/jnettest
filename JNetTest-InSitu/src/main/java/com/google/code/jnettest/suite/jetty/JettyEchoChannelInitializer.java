package com.google.code.jnettest.suite.jetty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

import com.google.code.jnettest.suite.conditions.Condition;

public class JettyEchoChannelInitializer<T extends Channel> extends ChannelInitializer<T> {

    protected final Condition continueTest;

    public JettyEchoChannelInitializer(Condition continueTest) {
        super();
        this.continueTest = continueTest;
    }

    @Override
    protected void initChannel(T ch) throws Exception {
        ch.pipeline().addLast(
//                new LoggingHandler(INFO),
                createEchoHandler()
                );
    }

    protected JettyEchoHandlerAdapter createEchoHandler() {
        return new JettyEchoHandlerAdapter(continueTest);
    }

}

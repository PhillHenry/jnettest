package com.google.code.jnettest.server.commands.jetty;

import static io.netty.channel.ChannelOption.SO_RCVBUF;
import static io.netty.channel.ChannelOption.SO_SNDBUF;

import java.util.HashSet;
import java.util.Set;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jnettest.server.context.Context;
import com.google.code.jnettest.suite.conditions.AtomicCounterCondition;
import com.google.code.jnettest.suite.conditions.CallbackConditionDecorator;
import com.google.code.jnettest.suite.conditions.CallbackConditionDecorator.ConditionCallback;
import com.google.code.jnettest.suite.conditions.Condition;
import com.google.code.jnettest.suite.conditions.TimerConditionDecorator;
import com.google.code.jnettest.suite.jetty.JettyClient;
import com.google.code.jnettest.suite.jetty.JettyConfigurer;
import com.google.code.jnettest.suite.jetty.JettyConfigurer.OptionValueTuple;
import com.google.code.jnettest.suite.jetty.JettyEchoChannelInitializer;
import com.google.code.jnettest.suite.jetty.JettyEchoInitiatingChannelInitializer;

public class StartTcpClientAction implements Action<Channel> {

    private static Logger         logger = LoggerFactory.getLogger(StartTcpServerAction.class);

    private final int             port;
    private final String          host;
    private final JettyConfigurer configurer;
    private final int             messageSize;

    private Condition finished;


    public StartTcpClientAction(Condition   finished,
                                int         port,
                                String      host,
                                int         messageSize,
                                int         sndBufferSize,
                                int         rcvBufferSize) {
        super();
        this.port               = port;
        this.host               = host;
        this.messageSize        = messageSize;
        Set<OptionValueTuple<?>> options = networkOptions(sndBufferSize,
                                                          rcvBufferSize);
        this.configurer         = new JettyConfigurer(options);
        this.finished           = finished;
    }

    private Set<OptionValueTuple<?>> networkOptions(int sndBufferSize,
                                                    int rcvBufferSize) {
        Set<OptionValueTuple<?>> options = new HashSet<>();
        options.add(new OptionValueTuple<Integer>(SO_RCVBUF, rcvBufferSize));
        options.add(new OptionValueTuple<Integer>(SO_SNDBUF, sndBufferSize));
        return options;
    }

    @Override
    public Channel execute(Context context) {
        try {
            return startClient();
        } catch (InterruptedException e) {
            logger.error("Could not start client", e);
            return null;
        }
    }

    private Channel startClient() throws InterruptedException {
        JettyClient jettyClient = new JettyClient(port,
                                                  host,
                                                  configurer);
        JettyEchoChannelInitializer<SocketChannel> initializer 
                                = new JettyEchoInitiatingChannelInitializer<>(finished, messageSize);
        Channel     channel     = jettyClient.start(initializer,
                                                    NioSocketChannel.class);
        return channel;
    }
    
    public boolean isFinished() {
        return !finished.isTrue();
    }

}

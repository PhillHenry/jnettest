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
import com.google.code.jnettest.suite.conditions.Condition;
import com.google.code.jnettest.suite.jetty.JettyClient;
import com.google.code.jnettest.suite.jetty.JettyConfigurer;
import com.google.code.jnettest.suite.jetty.JettyConfigurer.OptionValueTuple;
import com.google.code.jnettest.suite.jetty.JettyEchoChannelInitializer;

public class StartTcpClientAction implements Action<Channel> {

    private static Logger         logger = LoggerFactory.getLogger(StartTcpServerAction.class);

    private final int             numberOfExchanges;
    private final int             port;
    private final String          host;
    private final JettyConfigurer configurer;

    private Condition finished;

    public StartTcpClientAction(int     numberOfExchanges,
                                int     port,
                                String  host,
                                int     sndBufferSize,
                                int     rcvBufferSize) {
        super();
        this.numberOfExchanges  = numberOfExchanges;
        this.port               = port;
        this.host               = host;
        Set<OptionValueTuple<?>> options = new HashSet<>();
        options.add(new OptionValueTuple<Integer>(SO_RCVBUF, rcvBufferSize));
        options.add(new OptionValueTuple<Integer>(SO_SNDBUF, sndBufferSize));
        this.configurer         = new JettyConfigurer(options );
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
        finished = new AtomicCounterCondition(numberOfExchanges);
        JettyEchoChannelInitializer<SocketChannel> initializer 
                                = new JettyEchoChannelInitializer<>(finished);
        Channel     channel     = jettyClient.start(initializer,
                                                    NioSocketChannel.class);
        return channel;
    }
    
    public boolean isFinished() {
        return finished.isTrue();
    }

}

package com.google.code.jnettest.server.commands.jetty;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.google.code.jnettest.server.commands.Command;
import com.google.code.jnettest.server.context.Context;
import com.google.code.jnettest.suite.conditions.AtomicCounterCondition;
import com.google.code.jnettest.suite.conditions.Condition;
import com.google.code.jnettest.suite.jetty.JettyConfigurer;
import com.google.code.jnettest.suite.jetty.JettyConfigurer.OptionValueTuple;
import com.google.code.jnettest.suite.jetty.JettyEchoChannelInitializer;
import com.google.code.jnettest.suite.jetty.JettyServer;

public class StartTcpServerAction implements Action<Channel> {

    private static Logger         logger 
        = LoggerFactory.getLogger(StartTcpServerAction.class);

    private final int             numberOfExchanges;
    private final int             port;
    private final JettyConfigurer configurer;

    public StartTcpServerAction(int numberOfExchanges, int port,
            int rcvBufferSize) {
        super();
        this.numberOfExchanges          = numberOfExchanges;
        this.port                       = port;
        Set<OptionValueTuple<?>> params = new HashSet<>();
        params.add(new OptionValueTuple<Integer>(ChannelOption.SO_RCVBUF, rcvBufferSize));
        configurer                      = new JettyConfigurer(params);
    }

    public Channel execute(Context context) {
        try {
            return startServer(configurer);
        } catch (InterruptedException e) {
            logger.error("Could not start server", e);
            return null;
        }
    }

    private Channel startServer(JettyConfigurer configurer)
            throws InterruptedException {
        Condition                                   finished 
            = new AtomicCounterCondition(numberOfExchanges);
        JettyEchoChannelInitializer<SocketChannel>  initializer 
            = new JettyEchoChannelInitializer<SocketChannel>(finished);
        JettyServer                                 jettyServer 
            = new JettyServer(port, configurer);
        Channel                                     channel 
            = jettyServer.start(initializer, NioServerSocketChannel.class);
        return channel;
    }

}

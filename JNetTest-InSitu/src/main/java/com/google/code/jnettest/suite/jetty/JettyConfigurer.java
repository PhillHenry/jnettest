package com.google.code.jnettest.suite.jetty;

import java.io.Serializable;
import java.util.Set;

import io.netty.bootstrap.AbstractBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;

public class JettyConfigurer implements Serializable {

    public static class OptionValueTuple<T> implements Serializable {
        private final ChannelOption<T> channelOption;
        private final T value;
        
        public OptionValueTuple(ChannelOption<T> channelOption, T value) {
            super();
            this.channelOption = channelOption;
            this.value = value;
        }
    }
    
    private final Set<OptionValueTuple<?>> options;
    
    public JettyConfigurer(Set<OptionValueTuple<?>> options) { 
        this.options = options;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void configure(
            AbstractBootstrap<? extends AbstractBootstrap<?, ? extends Channel>, ? extends Channel> bootstrap) {
        if (options != null) {
            for (OptionValueTuple<?> tuple : options) {
                bootstrap.option((ChannelOption)tuple.channelOption, tuple.value);
            }
        }
    }
    
}

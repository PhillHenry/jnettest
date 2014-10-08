package com.google.code.jnettest.suite.jetty;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThreadFactory
    implements ThreadFactory {
    
    private final AtomicInteger id = new AtomicInteger();
    private final String name;
    
    public MyThreadFactory(String name) {
        super();
        this.name = name;
    }  

    @Override
    public Thread newThread(Runnable r) {
        return new Thread(r, name + id.incrementAndGet());
    }

}

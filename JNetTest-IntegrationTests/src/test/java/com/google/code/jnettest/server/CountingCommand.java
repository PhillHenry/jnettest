package com.google.code.jnettest.server;

import java.util.concurrent.atomic.AtomicInteger;

import com.google.code.jnettest.server.commands.Command;
import com.google.code.jnettest.server.context.Context;

public class CountingCommand implements Command {
    private AtomicInteger callCount = new AtomicInteger();

    @Override
    public void execute(Context context) {
        callCount.incrementAndGet();
    }
    
    public int getCallCount() {
        return callCount.get();
    }
}

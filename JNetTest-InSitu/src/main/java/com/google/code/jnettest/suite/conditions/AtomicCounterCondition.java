package com.google.code.jnettest.suite.conditions;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicCounterCondition implements Condition {
    
    private final AtomicInteger counter = new AtomicInteger();
    
    private final int maximum;
    
    public AtomicCounterCondition(int maximum) {
        super();
        this.maximum = maximum;
    }

    @Override
    public boolean isTrue() {
        return counter.get() < maximum;
    }

    @Override
    public void event() {
        counter.incrementAndGet();
    }

}

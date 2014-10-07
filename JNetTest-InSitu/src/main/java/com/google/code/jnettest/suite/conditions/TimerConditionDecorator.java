package com.google.code.jnettest.suite.conditions;

public class TimerConditionDecorator implements Condition {
    
    private final Condition condition;
    
    private volatile long startTime;
    
    private volatile long endTime;

    public TimerConditionDecorator(Condition condition) {
        super();
        this.condition = condition;
    }

    @Override
    public boolean isTrue() {
        return condition.isTrue();
    }

    @Override
    public int event() {
        int event = condition.event();
        
        if (event == 1) {
            startTime = System.currentTimeMillis();
        }
        if (event == condition.max()) {
            endTime = System.currentTimeMillis();
        }
        
        return event;
    }

    @Override
    public int max() {
        return condition.max();
    }
    
    public long durationMs() {
        return endTime - startTime;
    }

}

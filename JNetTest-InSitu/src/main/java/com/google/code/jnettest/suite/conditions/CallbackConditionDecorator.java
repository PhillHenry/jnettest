package com.google.code.jnettest.suite.conditions;

public class CallbackConditionDecorator implements Condition {
    
    public static interface ConditionCallback {
        public void callback();
    }
    
    private final Condition condition;
    
    private final ConditionCallback callback;
    
    public CallbackConditionDecorator(Condition condition, ConditionCallback callback) {
        super();
        this.condition = condition;
        this.callback = callback;
    }

    @Override
    public boolean isTrue() {
        return condition.isTrue();
    }

    @Override
    public int event() {
        int event = condition.event();
        
        if (event == max()) {
            callback.callback();
        }
        
        return event;
    }

    @Override
    public int max() {
        return condition.max();
    }

}

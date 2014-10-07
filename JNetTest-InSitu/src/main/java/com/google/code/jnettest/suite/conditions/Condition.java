package com.google.code.jnettest.suite.conditions;

public interface Condition {
    
    public boolean isTrue();
    
    public int event();
    
    public int max();
}

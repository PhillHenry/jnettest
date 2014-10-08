package com.google.code.jnettest.server.results;

import com.google.code.jnettest.server.app.Server;
import com.google.code.jnettest.server.commands.ReturnStatsCommand;
import com.google.code.jnettest.suite.conditions.AtomicCounterCondition;
import com.google.code.jnettest.suite.conditions.CallbackConditionDecorator;
import com.google.code.jnettest.suite.conditions.Condition;
import com.google.code.jnettest.suite.conditions.TimerConditionDecorator;
import com.google.code.jnettest.suite.conditions.CallbackConditionDecorator.ConditionCallback;

public class ResultConditionFactory {
    
    private final Server server;
    
    public ResultConditionFactory(Server server) {
        super();
        this.server = server;
    }

    public CallbackConditionDecorator createCallbackTimerCondition(int numberOfExchanges, String name) {
        Condition                   condition                   = new AtomicCounterCondition(numberOfExchanges);
        TimerConditionDecorator     timerConditionDecorator     = new TimerConditionDecorator(condition);
        ConditionCallback           callback                    = resultSendingCallback(timerConditionDecorator, name);
        CallbackConditionDecorator  callbackConditionDecorator  = new CallbackConditionDecorator(timerConditionDecorator,
                                                                                               callback);
        return callbackConditionDecorator;
    }

    private ConditionCallback resultSendingCallback(final TimerConditionDecorator timer, 
                                                    final String name) {
        return new ConditionCallback() {
            
            @Override
            public void callback() {
                server.execute(new ReturnStatsCommand(name, timer.durationMs()));
            }
        };
    }
}

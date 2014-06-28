package com.google.code.jnettest.server.commands;

import java.io.Serializable;

import com.google.code.jnettest.server.context.Context;

public interface Command extends Serializable {

    public void execute(Context context);
    
}

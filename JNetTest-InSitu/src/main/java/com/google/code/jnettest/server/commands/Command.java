package com.google.code.jnettest.server.commands;

import com.google.code.jnettest.server.context.Context;

public interface Command {

    public void execute(Context context);
    
}

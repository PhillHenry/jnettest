package com.google.code.jnettest.server.app;

import com.google.code.jnettest.server.commands.Command;

public interface CommandService {

    public Command execute(Command command);
    
}

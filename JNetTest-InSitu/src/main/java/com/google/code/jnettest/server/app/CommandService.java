package com.google.code.jnettest.server.app;

import com.google.code.jnettest.server.commands.Command;

public interface CommandService {

    public <T extends Command> T execute(T command);
    
}

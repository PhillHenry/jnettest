package com.google.code.jnettest.server.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.code.jnettest.server.commands.Command;
import com.google.code.jnettest.server.context.Context;

@Component
public class Server implements CommandService {
    
    @Autowired
    private Context context;

    @Override
    public <T extends Command> T execute(T command) {
        command.execute(context);
        return command;
    }

}

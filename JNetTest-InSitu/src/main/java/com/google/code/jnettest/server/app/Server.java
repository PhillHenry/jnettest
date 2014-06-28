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
    public Command execute(Command command) {
        command.execute(context);
        return command;
    }

}

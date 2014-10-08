package com.google.code.jnettest.server.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.jnettest.server.context.Context;

public class ReturnStatsCommand
    implements Command {
    
    private static final Logger logger = LoggerFactory.getLogger(ReturnStatsCommand.class);
    
    private final String name;
    private final long   time;

    public ReturnStatsCommand(String name,
                              long time) {
        super();
        this.name = name;
        this.time = time;
    }

    @Override
    public void execute(Context context) {
        logger.info(String.format("%s took %d ms", name, time)); // TODO 
    }

}

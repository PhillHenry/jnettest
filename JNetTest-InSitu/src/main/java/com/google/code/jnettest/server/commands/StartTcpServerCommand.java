package com.google.code.jnettest.server.commands;

import com.google.code.jnettest.server.commands.jetty.StartTcpServerAction;
import com.google.code.jnettest.server.context.Context;

public class StartTcpServerCommand implements Command {
    
    private final int   numberOfExchanges;
    private final int   port;
    private final int   rcvBuffer;            

    public StartTcpServerCommand(int numberOfExchanges, int port, int rcvBuffer) {
        super();
        this.numberOfExchanges = numberOfExchanges;
        this.port = port;
        this.rcvBuffer = rcvBuffer;
    }

    @Override
    public void execute(Context context) {
        StartTcpServerAction action = new StartTcpServerAction(numberOfExchanges, port, rcvBuffer);
        action.execute(context);
    }

}

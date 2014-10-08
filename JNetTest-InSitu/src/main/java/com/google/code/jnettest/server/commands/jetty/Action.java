package com.google.code.jnettest.server.commands.jetty;

import com.google.code.jnettest.server.context.Context;

public interface Action<T> {
    public T execute(Context context);
}

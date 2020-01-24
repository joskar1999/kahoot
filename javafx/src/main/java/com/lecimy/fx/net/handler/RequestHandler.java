package com.lecimy.fx.net.handler;

import com.lecimy.fx.listener.EventListener;

@FunctionalInterface
public interface RequestHandler {

    void handle(String message, EventListener[] eventListeners);
}

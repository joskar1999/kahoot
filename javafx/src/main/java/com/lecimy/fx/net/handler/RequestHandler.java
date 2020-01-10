package main.java.com.lecimy.fx.net.handler;

import main.java.com.lecimy.fx.listener.EventListener;

@FunctionalInterface
public interface RequestHandler {

    void handle(String message, EventListener[] eventListeners);
}

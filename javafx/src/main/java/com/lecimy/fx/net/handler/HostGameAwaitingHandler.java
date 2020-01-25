package main.java.com.lecimy.fx.net.handler;

import main.java.com.lecimy.fx.listener.EventListener;

import static main.java.com.lecimy.fx.net.ResponseMessages.NEW_USER;

public class HostGameAwaitingHandler implements RequestHandler {

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        if (NEW_USER.equals(message)) {

        } else {

        }
    }
}

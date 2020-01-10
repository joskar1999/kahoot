package main.java.com.lecimy.fx.net.handler;

import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.listener.OnFailureHostCreationListener;
import main.java.com.lecimy.fx.listener.OnSuccessHostCreationListener;

import static main.java.com.lecimy.fx.net.ResponseMessages.OK;

public class HostInitializationHandler implements RequestHandler {

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnSuccessHostCreationListener onSuccessHostCreationListener = (OnSuccessHostCreationListener) eventListeners[0];
        OnFailureHostCreationListener onFailureHostCreationListener = (OnFailureHostCreationListener) eventListeners[1];
        if (OK.equals(message)) {
            onSuccessHostCreationListener.onSuccess();
        } else {
            onFailureHostCreationListener.onFailure();
        }
    }
}

package main.java.com.lecimy.fx.net.handler;

import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.listener.OnFailureUserCreationListener;
import main.java.com.lecimy.fx.listener.OnSuccessUserCreationListener;

import static main.java.com.lecimy.fx.net.ResponseMessages.OK;

public class UserInitializationHandler implements RequestHandler {

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnSuccessUserCreationListener onSuccessUserCreationListener = (OnSuccessUserCreationListener) eventListeners[0];
        OnFailureUserCreationListener onFailureUserCreationListener = (OnFailureUserCreationListener) eventListeners[1];
        if (OK.equals(message)) {
            onSuccessUserCreationListener.onSuccess();
        } else {
            onFailureUserCreationListener.onFailure();
        }
    }
}

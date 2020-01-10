package main.java.com.lecimy.fx.net.handler;

import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.listener.OnFailureNickCreationListener;
import main.java.com.lecimy.fx.listener.OnSuccessNickCreationListener;

import static main.java.com.lecimy.fx.net.ResponseMessages.OK;

public class NickInitializationHandler implements RequestHandler {

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnSuccessNickCreationListener onSuccessNickCreationListener = (OnSuccessNickCreationListener) eventListeners[0];
        OnFailureNickCreationListener onFailureNickCreationListener = (OnFailureNickCreationListener) eventListeners[1];
        if (OK.equals(message)) {
            onSuccessNickCreationListener.onSuccess();
        } else {
            onFailureNickCreationListener.onFailure();
        }
    }
}

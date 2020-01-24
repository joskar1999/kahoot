package main.java.com.lecimy.fx.net.handler;

import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.listener.OnFailureGameInitializationListener;
import main.java.com.lecimy.fx.listener.OnSuccessGameInitializationListener;

import static main.java.com.lecimy.fx.net.ResponseMessages.OK;

public class GameInitializationHandler implements RequestHandler {

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnSuccessGameInitializationListener onSuccessGameInitializationListener = (OnSuccessGameInitializationListener) eventListeners[0];
        OnFailureGameInitializationListener onFailureGameInitializationListener = (OnFailureGameInitializationListener) eventListeners[1];
        if (OK.equals(message)) {
            onSuccessGameInitializationListener.onSuccess();
        } else {
            onFailureGameInitializationListener.onFailure();
        }
    }
}

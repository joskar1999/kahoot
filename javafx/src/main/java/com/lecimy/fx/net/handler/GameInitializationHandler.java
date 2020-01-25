package com.lecimy.fx.net.handler;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureGameInitializationListener;
import com.lecimy.fx.listener.OnSuccessGameInitializationListener;

import static com.lecimy.fx.net.ResponseMessages.OK;

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

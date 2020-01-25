package com.lecimy.fx.net.handler;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureUserCreationListener;
import com.lecimy.fx.listener.OnSuccessUserCreationListener;

import static com.lecimy.fx.net.ResponseMessages.OK;

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

package com.lecimy.fx.net.handler;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureHostCreationListener;
import com.lecimy.fx.listener.OnSuccessHostCreationListener;

import static com.lecimy.fx.net.ResponseMessages.OK;

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

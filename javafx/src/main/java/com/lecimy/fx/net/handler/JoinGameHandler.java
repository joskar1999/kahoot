package com.lecimy.fx.net.handler;

import com.lecimy.fx.listener.EventListener;
import com.lecimy.fx.listener.OnFailureJoinGameListener;
import com.lecimy.fx.listener.OnSuccessJoinGameListener;

import static com.lecimy.fx.net.ResponseMessages.OK;

public class JoinGameHandler implements RequestHandler {

    @Override
    public void handle(String message, EventListener[] eventListeners) {
        OnSuccessJoinGameListener onSuccessJoinGameListener = (OnSuccessJoinGameListener) eventListeners[0];
        OnFailureJoinGameListener onFailureJoinGameListener = (OnFailureJoinGameListener) eventListeners[1];
        if (OK.equals(message)) {
            onSuccessJoinGameListener.onSuccess();
        } else {
            onFailureJoinGameListener.onFailure();
        }
    }
}

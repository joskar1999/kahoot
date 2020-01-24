package main.java.com.lecimy.fx.net.handler;

import main.java.com.lecimy.fx.listener.EventListener;
import main.java.com.lecimy.fx.listener.OnFailureJoinGameListener;
import main.java.com.lecimy.fx.listener.OnSuccessJoinGameListener;

import static main.java.com.lecimy.fx.net.ResponseMessages.OK;

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

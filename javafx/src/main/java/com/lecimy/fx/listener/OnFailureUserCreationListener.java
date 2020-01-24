package main.java.com.lecimy.fx.listener;

@FunctionalInterface
public interface OnFailureUserCreationListener extends EventListener {

    void onFailure();
}

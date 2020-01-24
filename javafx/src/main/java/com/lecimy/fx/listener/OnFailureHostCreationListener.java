package com.lecimy.fx.listener;

@FunctionalInterface
public interface OnFailureHostCreationListener extends EventListener {

    void onFailure();
}

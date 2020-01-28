package com.lecimy.fx.listener;

@FunctionalInterface
public interface OnFailureListener extends EventListener {

    void onFailure();
}

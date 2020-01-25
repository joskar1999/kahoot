package com.lecimy.fx.listener;

import com.lecimy.fx.listener.EventListener;

@FunctionalInterface
public interface OnSuccessGameInitializationListener extends EventListener {

    void onSuccess();
}

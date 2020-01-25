package com.lecimy.fx.listener;

@FunctionalInterface
public interface OnNewPlayerListener extends EventListener {

    void onNewPlayer(int playersAmount);
}

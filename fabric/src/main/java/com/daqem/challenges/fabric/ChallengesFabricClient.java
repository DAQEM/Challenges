package com.daqem.challenges.fabric;

import com.daqem.challenges.client.ChallengesClient;
import com.daqem.challenges.platform.ChallengesClientPlatform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class ChallengesFabricClient implements ClientModInitializer, ChallengesClientPlatform {

    @Override
    public void onInitializeClient() {
        ChallengesClient.init(this);
        registerKeyBindings();
    }

    private static void registerKeyBindings() {
        KeyBindingHelper.registerKeyBinding(ChallengesClient.OPEN_MENU);
    }
}

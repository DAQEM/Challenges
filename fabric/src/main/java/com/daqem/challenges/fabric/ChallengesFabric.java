package com.daqem.challenges.fabric;

import com.daqem.challenges.Challenges;
import net.fabricmc.api.ModInitializer;

public class ChallengesFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Challenges.init();
    }
}
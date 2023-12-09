package com.daqem.challenges.fabric;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.fabric.data.ChallengeManagerFabric;
import com.daqem.challenges.platform.ChallengesCommonPlatform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.server.packs.PackType;

public class ChallengesFabric implements ModInitializer, ChallengesCommonPlatform {

    private final ChallengeManagerFabric challengeManager = new ChallengeManagerFabric();
    private static ChallengesFabric instance;

    @Override
    public void onInitialize() {
        instance = this;

        Challenges.init(this);

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(challengeManager);
    }

    public static ChallengesFabric getInstance() {
        return instance != null ? instance : new ChallengesFabric();
    }

    public ChallengeManagerFabric getChallengeManager() {
        return challengeManager;
    }
}
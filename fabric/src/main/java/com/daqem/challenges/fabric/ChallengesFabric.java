package com.daqem.challenges.fabric;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.command.argument.ChallengeArgument;
import com.daqem.challenges.fabric.data.ChallengeManagerFabric;
import com.daqem.challenges.platform.ChallengesCommonPlatform;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.server.packs.PackType;

public class ChallengesFabric implements ModInitializer, ChallengesCommonPlatform {

    private final ChallengeManagerFabric challengeManager = new ChallengeManagerFabric();

    @Override
    public void onInitialize() {

        Challenges.init(this);

        ResourceManagerHelper.get(PackType.SERVER_DATA).registerReloadListener(challengeManager);

        registerCommandArgumentTypes();
    }

    private void registerCommandArgumentTypes() {
        ArgumentTypeRegistry.registerArgumentType(Challenges.getId("challenge"), ChallengeArgument.class, SingletonArgumentInfo.contextFree(ChallengeArgument::challenge));
    }

    public ChallengeManagerFabric getChallengeManager() {
        return challengeManager;
    }
}
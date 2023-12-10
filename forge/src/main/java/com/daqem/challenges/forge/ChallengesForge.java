package com.daqem.challenges.forge;

import com.daqem.challenges.forge.data.ChallengeManagerForge;
import com.daqem.challenges.platform.ChallengesCommonPlatform;
import dev.architectury.platform.forge.EventBuses;
import com.daqem.challenges.Challenges;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Challenges.MOD_ID)
public class ChallengesForge implements ChallengesCommonPlatform {

    private final ChallengeManagerForge challengeManager = new ChallengeManagerForge();

    public ChallengesForge() {
        EventBuses.registerModEventBus(Challenges.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        eventBus.addListener(this::onAddReloadListeners);
    }

    public void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(challengeManager);
    }

    public ChallengeManagerForge getChallengeManager() {
        return challengeManager;
    }
}
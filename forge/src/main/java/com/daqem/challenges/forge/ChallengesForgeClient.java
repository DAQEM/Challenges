package com.daqem.challenges.forge;

import com.daqem.challenges.client.ChallengesClient;
import com.daqem.challenges.platform.ChallengesClientPlatform;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class ChallengesForgeClient implements ChallengesClientPlatform {

    public ChallengesForgeClient() {
        ChallengesClient.init(this);
        registerEvents();
    }

    private void registerEvents() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::registerKeyBindings);
    }

    private void registerKeyBindings(RegisterKeyMappingsEvent event) {
        event.register(ChallengesClient.OPEN_MENU);
    }
}

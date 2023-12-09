package com.daqem.challenges.forge;

import dev.architectury.platform.forge.EventBuses;
import com.daqem.challenges.Challenges;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Challenges.MOD_ID)
public class ChallengesForge {
    public ChallengesForge() {
		// Submit our event bus to let architectury register our content on the right time
        EventBuses.registerModEventBus(Challenges.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        Challenges.init();
    }
}
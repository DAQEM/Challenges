package com.daqem.challenges.forge;

import com.daqem.challenges.command.argument.ChallengeArgument;
import com.daqem.challenges.forge.data.ChallengeManagerForge;
import com.daqem.challenges.platform.ChallengesCommonPlatform;
import dev.architectury.platform.forge.EventBuses;
import com.daqem.challenges.Challenges;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

@Mod(Challenges.MOD_ID)
public class ChallengesForge implements ChallengesCommonPlatform {

    private final ChallengeManagerForge challengeManager = new ChallengeManagerForge();

    public ChallengesForge() {
        EventBuses.registerModEventBus(Challenges.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        IEventBus eventBus = MinecraftForge.EVENT_BUS;
        eventBus.addListener(this::onAddReloadListeners);

        registerCommandArgumentTypes();

        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> ChallengesForgeClient::new);
    }

    private void registerCommandArgumentTypes() {
        DeferredRegister<ArgumentTypeInfo<?, ?>> argTypeRegistry = DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, Challenges.MOD_ID);
        argTypeRegistry.register("challenge", () -> ArgumentTypeInfos.registerByClass(ChallengeArgument.class, SingletonArgumentInfo.contextFree(ChallengeArgument::challenge)));
        argTypeRegistry.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(challengeManager);
    }

    public ChallengeManagerForge getChallengeManager() {
        return challengeManager;
    }
}
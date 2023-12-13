package com.daqem.challenges;

import com.daqem.challenges.event.RegisterCommandsEvent;
import com.daqem.challenges.holder.ChallengesActionHolderType;
import com.daqem.challenges.platform.ChallengesCommonPlatform;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class Challenges
{
	public static final String MOD_ID = "challenges";
	public static final Logger LOGGER = LogUtils.getLogger();
	private static ChallengesCommonPlatform platform;

	public static void init(ChallengesCommonPlatform platform) {
		Challenges.platform = platform;

		ChallengesActionHolderType.init();

		registerEvents();
	}

	private static void registerEvents() {
		RegisterCommandsEvent.registerEvent();
	}

	public static ResourceLocation getId(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static ChallengesCommonPlatform getPlatform() {
		return platform;
	}

	public static MutableComponent translatable(String str) {
		return Component.translatable(MOD_ID + "." + str);
	}
}

package com.daqem.challenges;

import com.daqem.challenges.event.RegisterCommandsEvent;
import com.daqem.challenges.integration.arc.holder.ChallengesActionHolderType;
import com.daqem.challenges.integration.arc.reward.ChallengesRewardSerializer;
import com.daqem.challenges.integration.arc.reward.ChallengesRewardType;
import com.daqem.challenges.platform.ChallengesCommonPlatform;
import com.mojang.logging.LogUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
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
		ChallengesRewardType.init();
		ChallengesRewardSerializer.init();

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

	public static MutableComponent literal(String str) {
		return Component.literal(str);
	}

	public static MutableComponent translatable(String str) {
		return translatable(str, TranslatableContents.NO_ARGS);
	}

	public static MutableComponent translatable(String str, Object... args) {
		return Component.translatable(MOD_ID + "." + str, args);
	}

	public static MutableComponent getChatPrefix() {
		return Component.empty().append(
				literal("[").withStyle(Style.EMPTY.withColor(0x0066CC))
		).append(
				translatable("name").withStyle(Style.EMPTY.withColor(0x33BBFF))
		).append(
				literal("] ").withStyle(Style.EMPTY.withColor(0x0066CC))
		);
	}
}

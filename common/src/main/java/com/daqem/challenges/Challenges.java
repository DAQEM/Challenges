package com.daqem.challenges;

import com.daqem.challenges.platform.ChallengesCommonPlatform;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

public class Challenges
{
	public static final String MOD_ID = "challenges";
	public static final Logger LOGGER = LogUtils.getLogger();
	private static ChallengesCommonPlatform platform;

	public static void init(ChallengesCommonPlatform platform) {
		Challenges.platform = platform;
	}

	public static ResourceLocation getId(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static ChallengesCommonPlatform getPlatform() {
		return platform;
	}
}

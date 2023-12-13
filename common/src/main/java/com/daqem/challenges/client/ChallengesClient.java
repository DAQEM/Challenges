package com.daqem.challenges.client;

import com.daqem.challenges.client.event.KeyPressedEvent;
import com.daqem.challenges.platform.ChallengesClientPlatform;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;

public class ChallengesClient {

    private static final String CHALLENGES_CATEGORY = "key.categories.challenges";
    public static final KeyMapping OPEN_MENU = new KeyMapping("key.challenges.open_menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_C, CHALLENGES_CATEGORY);


    public static void init(ChallengesClientPlatform platform) {
        registerEvents();
    }

    private static void registerEvents() {
        KeyPressedEvent.registerEvent();
    }
}

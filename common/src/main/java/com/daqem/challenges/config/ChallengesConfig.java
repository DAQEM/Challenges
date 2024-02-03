package com.daqem.challenges.config;

import com.daqem.challenges.Challenges;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;

import java.util.function.Supplier;

public class ChallengesConfig {

    public static void init() {
    }

    public static final Supplier<Boolean> isDebug;

    public static final Supplier<Integer> primaryColor;
    public static final Supplier<Integer> secondaryColor;
    public static final Supplier<Integer> easyColor;
    public static final Supplier<Integer> mediumColor;
    public static final Supplier<Integer> hardColor;

    static {
        IConfigBuilder config = ConfigBuilders.newTomlConfig(Challenges.MOD_ID, null, false);

        config.push("debug");
        isDebug = config.comment("If true, debug messages will be printed to the console.").define("isDebug", false);
        config.pop();

        config.push("colors");
        primaryColor = config.comment("The primary color used for the mod.").define("primaryColor", 0xaaaaaa, 0x000000, 0xFFFFFF);
        secondaryColor = config.comment("The secondary color used for the mod.").define("secondaryColor", 0x555555, 0x000000, 0xFFFFFF);
        easyColor = config.comment("The color used for easy challenges.").define("easyColor", 0x52d149, 0x000000, 0xFFFFFF);
        mediumColor = config.comment("The color used for medium challenges.").define("mediumColor", 0xed992b, 0x000000, 0xFFFFFF);
        hardColor = config.comment("The color used for hard challenges.").define("hardColor", 0xe63a2e, 0x000000, 0xFFFFFF);
        config.pop();

        config.build();
    }

}

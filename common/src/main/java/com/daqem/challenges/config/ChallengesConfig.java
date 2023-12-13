package com.daqem.challenges.config;

import com.daqem.challenges.Challenges;
import com.supermartijn642.configlib.api.ConfigBuilders;
import com.supermartijn642.configlib.api.IConfigBuilder;

import java.util.function.Supplier;

public class ChallengesConfig {

    public static void init() {
    }

    public static final Supplier<Boolean> isDebug;

    static {
        IConfigBuilder config = ConfigBuilders.newTomlConfig(Challenges.MOD_ID, null, false);
        config.push("debug");
        isDebug = config.comment("If true, debug messages will be printed to the console.").define("isDebug", false);
        config.pop();
        config.build();
    }

}

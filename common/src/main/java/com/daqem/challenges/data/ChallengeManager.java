package com.daqem.challenges.data;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ChallengeManager extends SimpleJsonResourceReloadListener {

    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping()
            .registerTypeHierarchyAdapter(Challenge.class, new Challenge.Serializer())
            .create();
    private static final Logger LOGGER = LogUtils.getLogger();

    private ImmutableMap<ResourceLocation, Challenge> challenges = ImmutableMap.of();

    public ChallengeManager() {
        super(GSON, "challenges/challenges");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        Map<ResourceLocation, Challenge> tempChallenges = new HashMap<>();

        map.forEach((location, jsonElement) -> {
            String locationString = location.toString();
            JsonObject json = jsonElement.getAsJsonObject();
            json.addProperty("_location", locationString);
            try {
                tempChallenges.put(location, GSON.fromJson(json, Challenge.class));
            } catch (Exception e) {
                LOGGER.error("Could not deserialize challenge {} because: {}", locationString, e.getMessage());
            }
        });

        LOGGER.info("Loaded {} challenges", tempChallenges.size());
        this.challenges = ImmutableMap.copyOf(tempChallenges);

        LOGGER.error("{}", Challenges.getPlatform().getChallengeManager().challenges.size());
    }
}

package com.daqem.challenges.data;

import com.daqem.arc.api.action.holder.ActionHolderManager;
import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.holder.ChallengesActionHolderType;
import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.slf4j.Logger;

import java.util.*;

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
        ActionHolderManager.getInstance().clearActionHolders(ChallengesActionHolderType.CHALLENGE);
        Map<ResourceLocation, Challenge> tempChallenges = new HashMap<>();

        map.forEach((location, jsonElement) -> {
            String locationString = location.toString();
            JsonObject json = jsonElement.getAsJsonObject();
            json.addProperty("_location", locationString);
            try {
                Challenge challenge = GSON.fromJson(json, Challenge.class);
                tempChallenges.put(location, challenge);
                ActionHolderManager.getInstance().registerActionHolder(challenge);
            } catch (JsonSyntaxException | UnsupportedOperationException | ClassCastException | NullPointerException | IllegalArgumentException e) {
                LOGGER.error("Could not deserialize challenge {} because: {}", locationString, e.getMessage());
            }
        });

        LOGGER.info("Loaded {} challenges", tempChallenges.size());
        this.challenges = ImmutableMap.copyOf(tempChallenges);
    }

    public Challenge getChallenge(ResourceLocation location) {
        return challenges.get(location);
    }

    public ImmutableMap<ResourceLocation, Challenge> getChallengesMap() {
        return challenges;
    }

    public List<Challenge> getChallengesList() {
        return challenges.values().stream().toList();
    }

    public void replaceChallenges(List<Challenge> challenges) {
        Map<ResourceLocation, Challenge> tempChallenges = new HashMap<>();
        challenges.forEach(challenge -> {
            tempChallenges.put(challenge.getLocation(), challenge);
            ActionHolderManager.getInstance().registerActionHolder(challenge);
        });
        this.challenges = ImmutableMap.copyOf(tempChallenges);
        Challenges.LOGGER.info("Updated {} challenges", tempChallenges.size());
    }

    public Challenge getRandomChallenge() {
        Random random = new Random();
        List<Challenge> challenges = getChallengesList();
        return challenges.get(random.nextInt(challenges.size()));
    }

    public List<Challenge> getThreeRandomChallenges() {
        List<Challenge> allChallenges = getChallengesList();
        Collections.shuffle(allChallenges, new Random());
        return allChallenges.subList(0, Math.min(allChallenges.size(), 3));
    }

    public List<String> getChallengeNamespaces() {
        return getChallengesMap().keySet().stream().map(ResourceLocation::getNamespace).distinct().toList();
    }

    public List<ResourceLocation> getChallengeLocations() {
        return getChallengesMap().keySet().stream().toList();
    }

    public List<String> getChallengeLocationStrings() {
        return getChallengesMap().keySet().stream().map(ResourceLocation::toString).toList();
    }
}

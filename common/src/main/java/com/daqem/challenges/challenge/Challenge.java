package com.daqem.challenges.challenge;

import com.daqem.arc.api.action.IAction;
import com.daqem.arc.api.action.holder.IActionHolder;
import com.daqem.arc.api.action.holder.type.IActionHolderType;
import com.daqem.arc.api.reward.IReward;
import com.daqem.arc.api.reward.serializer.IRewardSerializer;
import com.daqem.arc.registry.ArcRegistry;
import com.daqem.challenges.Challenges;
import com.daqem.challenges.data.ChallengesSerializer;
import com.daqem.challenges.integration.arc.holder.ChallengesActionHolderType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Challenge implements IActionHolder {

    private final Map<ResourceLocation, IAction> actions = new HashMap<>();
    private final ResourceLocation location;
    private final int goal;
    private final Difficulty difficulty;
    private final ResourceLocation imageLocation;
    private final List<IReward> rewards;

    public Challenge(ResourceLocation location, int goal, Difficulty difficulty, ResourceLocation imageLocation, List<IReward> rewards) {
        this.location = location;
        this.goal = goal;
        this.difficulty = difficulty;
        this.imageLocation = imageLocation;
        this.rewards = rewards;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    @Override
    public List<IAction> getActions() {
        return actions.values().stream().toList();
    }

    @Override
    public void addAction(IAction action) {
        actions.put(action.getLocation(), action);
    }

    @Override
    public IActionHolderType<Challenge> getType() {
        return ChallengesActionHolderType.CHALLENGE;
    }

    public int getGoal() {
        return goal;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public ResourceLocation getImageLocation() {
        return imageLocation;
    }

    public List<IReward> getRewards() {
        return rewards;
    }

    public MutableComponent getName() {
        return Challenges.translatable("challenge." + location.getNamespace() + "." + location.getPath() + ".name");
    }

    public MutableComponent getStyledName() {
        return getName().withStyle(Style.EMPTY.withColor(getDifficulty().getColor()));
    }

    public MutableComponent getDescription() {
        return Challenges.translatable("challenge." + location.getNamespace() + "." + location.getPath() + ".description");
    }

    public MutableComponent getChallengeCompleteMessage(ServerPlayer player) {
        return Challenges.getChatPrefix().append(Challenges.translatable("challenge.complete", player.getName(), getDifficulty().getLowercaseStyledDisplayName(), getStyledName()));
    }

    public static class Serializer implements ChallengesSerializer<Challenge> {

        private static final String LOCATION = "location";
        private static final String GOAL = "goal";
        private static final String DIFFICULTY = "difficulty";
        private static final String IMAGE = "image";

        private static final String DEFAULT_IMAGE = "textures/gui/images/unknown.png";

        @Override
        public Challenge deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();

            ResourceLocation location = getResourceLocation(json, "_location");
            List<IReward> rewards = new ArrayList<>();
            if (json.has("rewards")) {
                json.getAsJsonArray("rewards").forEach(json1 -> {
                    ResourceLocation rewardLocation = getResourceLocation(json1.getAsJsonObject(), "type");
                    ArcRegistry.REWARD_SERIALIZER.getOptional(rewardLocation).ifPresent(serializer -> {
                        rewards.add(serializer.fromJson(location, json1.getAsJsonObject()));
                    });
                });
            }

            return new Challenge(
                    location,
                    json.get(GOAL).getAsInt(),
                    Difficulty.getById(json.get(DIFFICULTY).getAsInt()),
                    json.has(IMAGE) ? getResourceLocation(json, IMAGE) : Challenges.getId(DEFAULT_IMAGE),
                    rewards
            );
        }

        @Override
        public Challenge fromNetwork(FriendlyByteBuf friendlyByteBuf) {
            return new Challenge(
                    friendlyByteBuf.readResourceLocation(),
                    friendlyByteBuf.readInt(),
                    Difficulty.getById(friendlyByteBuf.readInt()),
                    friendlyByteBuf.readResourceLocation(),
                    friendlyByteBuf.readList(IRewardSerializer::fromNetwork)
                    );
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, Challenge challenge) {
            friendlyByteBuf.writeResourceLocation(challenge.getLocation());
            friendlyByteBuf.writeInt(challenge.getGoal());
            friendlyByteBuf.writeInt(challenge.getDifficulty().getId());
            friendlyByteBuf.writeResourceLocation(challenge.getImageLocation());
            friendlyByteBuf.writeCollection(challenge.getRewards(),
                    (friendlyByteBuf1, reward) -> IRewardSerializer.toNetwork(reward, friendlyByteBuf1, challenge.getLocation()));
        }

        @Override
        public Challenge fromNBT(CompoundTag compoundTag) {
            return Challenges.getPlatform().getChallengeManager().getChallenge(new ResourceLocation(compoundTag.getString(LOCATION))).orElse(null);
        }

        @Override
        public CompoundTag toNBT(Challenge type) {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.putString(LOCATION, type.getLocation().toString());
            return compoundTag;
        }
    }
}

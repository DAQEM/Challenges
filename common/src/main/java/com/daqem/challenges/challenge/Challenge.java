package com.daqem.challenges.challenge;

import com.daqem.arc.api.action.IAction;
import com.daqem.arc.api.action.holder.IActionHolder;
import com.daqem.arc.api.action.holder.type.IActionHolderType;
import com.daqem.challenges.Challenges;
import com.daqem.challenges.data.ChallengesSerializer;
import com.daqem.challenges.holder.ChallengesActionHolderType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Challenge implements IActionHolder {

    private final Map<ResourceLocation, IAction> actions = new HashMap<>();
    private final ResourceLocation location;
    private final int goal;
    private final Difficulty difficulty;

    public Challenge(ResourceLocation location, int goal, Difficulty difficulty) {
        this.location = location;
        this.goal = goal;
        this.difficulty = difficulty;
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

    public Component getName() {
        return Challenges.translatable("challenge." + location.getNamespace() + "." + location.getPath() + ".name");
    }

    public Component getDescription() {
        return Challenges.translatable("challenge." + location.getNamespace() + "." + location.getPath() + ".description");
    }

    public static class Serializer implements ChallengesSerializer<Challenge> {

        @Override
        public Challenge deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();
            return new Challenge(
                    getResourceLocation(json, "_location"),
                    json.get("goal").getAsInt(),
                    Difficulty.valueOf(json.get("difficulty").getAsString())
            );
        }

        @Override
        public Challenge fromNetwork(FriendlyByteBuf friendlyByteBuf) {
            return new Challenge(
                    friendlyByteBuf.readResourceLocation(),
                    friendlyByteBuf.readInt(),
                    Difficulty.getById(friendlyByteBuf.readInt())
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, Challenge challenge) {
            friendlyByteBuf.writeResourceLocation(challenge.getLocation());
            friendlyByteBuf.writeInt(challenge.getGoal());
            friendlyByteBuf.writeInt(challenge.getDifficulty().getId());
        }
    }
}

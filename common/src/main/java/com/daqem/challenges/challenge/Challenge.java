package com.daqem.challenges.challenge;

import com.daqem.challenges.data.ChallengesSerializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.lang.reflect.Type;

public class Challenge {

    private final ResourceLocation location;
    private final int goal;

    public Challenge(ResourceLocation location, int goal) {
        this.location = location;
        this.goal = goal;
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public int getGoal() {
        return goal;
    }

    public static class Serializer implements ChallengesSerializer<Challenge> {

        @Override
        public Challenge deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            JsonObject json = jsonElement.getAsJsonObject();
            return new Challenge(
                    getResourceLocation(json, "_location"),
                    json.get("goal").getAsInt());
        }

        @Override
        public Challenge fromNetwork(FriendlyByteBuf friendlyByteBuf) {
            return new Challenge(
                    friendlyByteBuf.readResourceLocation(),
                    friendlyByteBuf.readInt()
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, Challenge challenge) {
            friendlyByteBuf.writeResourceLocation(challenge.getLocation());
            friendlyByteBuf.writeInt(challenge.getGoal());
        }
    }
}

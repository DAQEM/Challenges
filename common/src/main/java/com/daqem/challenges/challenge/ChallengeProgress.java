package com.daqem.challenges.challenge;

import com.daqem.challenges.data.ChallengesSerializer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

import java.lang.reflect.Type;

public class ChallengeProgress {

    private final Challenge challenge;
    private int progress;

    public ChallengeProgress(Challenge challenge) {
        this(challenge, 0);
    }

    public ChallengeProgress(Challenge challenge, int progress) {
        this.challenge = challenge;
        this.progress = progress;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public int getProgress() {
        return progress;
    }

    public double getPercentComplete() {
        return Mth.clamp((double) progress / challenge.getGoal(), 0, 1);
    }

    public void incrementProgress() {
        incrementProgress(1);
    }

    public void incrementProgress(int amount) {
        progress += amount;
    }

    public boolean isComplete() {
        return progress >= challenge.getGoal();
    }

    public static class Serializer implements ChallengesSerializer<ChallengeProgress> {

        @Override
        public ChallengeProgress fromNetwork(FriendlyByteBuf friendlyByteBuf) {
            return new ChallengeProgress(
                    new Challenge.Serializer().fromNetwork(friendlyByteBuf),
                    friendlyByteBuf.readInt()
            );
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, ChallengeProgress type) {
            new Challenge.Serializer().toNetwork(friendlyByteBuf, type.getChallenge());
            friendlyByteBuf.writeInt(type.getProgress());
        }

        @Override
        public ChallengeProgress deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new ChallengeProgress(
                    new Challenge.Serializer().deserialize(jsonElement, type, jsonDeserializationContext),
                    jsonElement.getAsJsonObject().get("progress").getAsInt()
            );
        }
    }
}

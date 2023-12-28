package com.daqem.challenges.challenge;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.data.ChallengesSerializer;
import com.daqem.challenges.player.ChallengesServerPlayer;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class ChallengeProgress {

    private final @NotNull Challenge challenge;
    private int progress;

    public ChallengeProgress(@NotNull Challenge challenge) {
        this(challenge, 0);
    }

    public ChallengeProgress(@NotNull Challenge challenge, int progress) {
        this.challenge = challenge;
        this.progress = progress;
    }

    public @NotNull Challenge getChallenge() {
        return challenge;
    }

    public int getProgress() {
        return progress;
    }

    public double getPercentComplete() {
        return Mth.clamp((double) progress / challenge.getGoal(), 0, 1);
    }

    public void setProgress(ChallengesServerPlayer player, int progress) {
        this.progress = Mth.clamp(progress, 0, challenge.getGoal());

        if (isComplete()) {
            complete(player);
        }
    }

    public void incrementProgress(ChallengesServerPlayer player, int amount) {
        setProgress(player, progress + amount);
    }

    public boolean isComplete() {
        return progress >= challenge.getGoal();
    }

    public void complete(ChallengesServerPlayer player) {
        if (player instanceof ServerPlayer serverPlayer) {
            player.challenges$getChallenge().ifPresent(challengeProgress ->
                    serverPlayer.server.getPlayerList().broadcastSystemMessage(
                            challengeProgress.getChallenge().getChallengeCompleteMessage(serverPlayer),
                            false));
        }
        player.challenges$removeChallenge();
    }

    public static class Serializer implements ChallengesSerializer<ChallengeProgress> {

        private static final String CHALLENGE = "challenge";
        private static final String PROGRESS = "progress";

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
        public ChallengeProgress fromNBT(CompoundTag compoundTag) {
            Challenge challenge = new Challenge.Serializer().fromNBT(compoundTag.getCompound(CHALLENGE));
            if (challenge == null) {
                return null;
            }
            return new ChallengeProgress(challenge, compoundTag.getInt(PROGRESS));
        }

        @Override
        public CompoundTag toNBT(ChallengeProgress type) {
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.put(CHALLENGE, new Challenge.Serializer().toNBT(type.getChallenge()));
            compoundTag.putInt(PROGRESS, type.getProgress());
            return compoundTag;
        }

        @Override
        public ChallengeProgress deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return new ChallengeProgress(
                    new Challenge.Serializer().deserialize(jsonElement, type, jsonDeserializationContext),
                    jsonElement.getAsJsonObject().get(PROGRESS).getAsInt()
            );
        }
    }
}

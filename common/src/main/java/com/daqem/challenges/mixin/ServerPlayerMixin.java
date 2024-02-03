package com.daqem.challenges.mixin;

import com.daqem.arc.api.player.ArcServerPlayer;
import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.challenge.ChallengeProgress;
import com.daqem.challenges.integration.arc.holder.ChallengesActionHolderType;
import com.daqem.challenges.networking.clientbound.ClientboundUpdateChallengesPacket;
import com.daqem.challenges.player.ChallengesServerPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements ChallengesServerPlayer {

    @Unique
    private static final String challenges1_20_1$CHALLENGES = "Challenges";
    @Unique
    private static final String challenges1_20_1$CHALLENGE = "Challenge";
    @Unique
    private static final String challenges1_20_1$CACHED_CHALLENGES = "CachedChallenges";
    
    @Unique
    private ChallengeProgress challenges$challenge;

    @Unique
    private List<Challenge> challenges$cachedChallenges = new ArrayList<>();

    public ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Unique
    @Override
    public Optional<ChallengeProgress> challenges$getChallenge() {
        return Optional.ofNullable(challenges$challenge);
    }

    @Unique
    @Override
    public void challenges$setChallenge(ChallengeProgress challenge) {
        challenges$challenge = challenge;
        if (this instanceof ArcServerPlayer player) {
            player.arc$getActionHolders().removeIf(x -> x == null || x.getType().equals(ChallengesActionHolderType.CHALLENGE));
            if (challenge != null) {
                player.arc$addActionHolder(challenge.getChallenge());
            }
        }
    }

    @Unique
    @Override
    public void challenges$setChallengeIfNotPresent(Challenge challenge) {
        challenges$getChallenge().ifPresentOrElse(
                x -> {},
                () -> challenges$setChallenge(challenge)
        );
    }

    @Unique
    @Override
    public void challenges$setChallengeProgress(int progress) {
        challenges$getChallenge().ifPresent(
                x -> x.setProgress(this, progress)
        );
    }

    @Unique
    @Override
    public void challenges$setChallenge(Challenge challenge) {
        challenges$setChallenge(new ChallengeProgress(challenge));
    }

    @Unique
    @Override
    public void challenges$removeChallenge() {
        challenges$setChallenge((ChallengeProgress) null);
    }

    @Unique
    @Override
    public List<Challenge> challenges$getCachedChallenges() {
        return challenges$cachedChallenges;
    }

    @Unique
    @Override
    public void challenges$setCachedChallenges(List<Challenge> challenges) {
        challenges$cachedChallenges = new ArrayList<>(challenges);
    }

    @Unique
    @Override
    public void challenges$syncChallenges() {
        new ClientboundUpdateChallengesPacket(Challenges.getPlatform().getChallengeManager().getChallengesList()).sendTo((ServerPlayer) (Object) this);
    }

    @Inject(at = @At("TAIL"), method = "restoreFrom(Lnet/minecraft/server/level/ServerPlayer;Z)V")
    public void restoreFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        if (oldPlayer instanceof ChallengesServerPlayer oldChallengesPlayer) {
            oldChallengesPlayer.challenges$getChallenge().ifPresent(challengeProgress -> {
                this.challenges$challenge = challengeProgress;
            });
            this.challenges$cachedChallenges = oldChallengesPlayer.challenges$getCachedChallenges();
        }
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {

        CompoundTag challengesTag = new CompoundTag();

        this.challenges$getChallenge().ifPresent(challengeProgress ->
                challengesTag.put(challenges1_20_1$CHALLENGE, new ChallengeProgress.Serializer().toNBT(challengeProgress)));

        CompoundTag cachedChallengesTag = new CompoundTag();
        challenges$cachedChallenges.forEach(challenge ->
                cachedChallengesTag.put(challenge.getLocation().toString(), new Challenge.Serializer().toNBT(challenge)));
        challengesTag.put(challenges1_20_1$CACHED_CHALLENGES, cachedChallengesTag);

        compoundTag.put(challenges1_20_1$CHALLENGES, challengesTag);
    }

    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        CompoundTag challengesTag = compoundTag.getCompound(challenges1_20_1$CHALLENGES);
        this.challenges$challenge = new ChallengeProgress.Serializer().fromNBT(challengesTag.getCompound(challenges1_20_1$CHALLENGE));
        CompoundTag cachedChallengesTag = challengesTag.getCompound(challenges1_20_1$CACHED_CHALLENGES);
        this.challenges$cachedChallenges = cachedChallengesTag.getAllKeys().stream().map(key ->
                new Challenge.Serializer().fromNBT(cachedChallengesTag.getCompound(key)))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (this.challenges$cachedChallenges.size() < 3 && this.challenges$getChallenge().isEmpty()) {
            this.challenges$cachedChallenges = Challenges.getPlatform().getChallengeManager().getThreeRandomChallenges();
        }

        if (this instanceof ArcServerPlayer arcServerPlayer) {
            challenges$getChallenge().ifPresent(challengeProgress -> {
                arcServerPlayer.arc$addActionHolder(challengeProgress.getChallenge());
            });
        }
    }
}

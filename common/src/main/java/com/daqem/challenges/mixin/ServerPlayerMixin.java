package com.daqem.challenges.mixin;

import com.daqem.arc.api.player.ArcServerPlayer;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.challenge.ChallengeProgress;
import com.daqem.challenges.integration.arc.holder.ChallengesActionHolderType;
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

import java.util.Optional;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements ChallengesServerPlayer {

    private static final String CHALLENGES = "Challenges";
    private static final String CHALLENGE = "Challenge";
    
    @Unique
    private ChallengeProgress challenges$challenge;

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
            player.arc$getActionHolders().removeIf(x -> x.getType().equals(ChallengesActionHolderType.CHALLENGE));
            if (challenge != null) {
                player.arc$getActionHolders().add(challenge.getChallenge());
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

    @Inject(at = @At("TAIL"), method = "restoreFrom(Lnet/minecraft/server/level/ServerPlayer;Z)V")
    public void restoreFrom(ServerPlayer oldPlayer, boolean alive, CallbackInfo ci) {
        if (oldPlayer instanceof ChallengesServerPlayer oldChallengesPlayer) {
            oldChallengesPlayer.challenges$getChallenge().ifPresent(challengeProgress -> {
                this.challenges$challenge = challengeProgress;
            });
        }
    }

    @Inject(at = @At("TAIL"), method = "addAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
    public void addAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {

        this.challenges$getChallenge().ifPresent(challengeProgress -> {
            CompoundTag challengesTag = new CompoundTag();
            challengesTag.put(CHALLENGE, new ChallengeProgress.Serializer().toNBT(challengeProgress));
            compoundTag.put(CHALLENGES, challengesTag);
        });
    }

    @Inject(at = @At("TAIL"), method = "readAdditionalSaveData(Lnet/minecraft/nbt/CompoundTag;)V")
    public void readAdditionalSaveData(CompoundTag compoundTag, CallbackInfo ci) {
        CompoundTag challengesTag = compoundTag.getCompound(CHALLENGES);
        this.challenges$challenge = new ChallengeProgress.Serializer().fromNBT(challengesTag.getCompound(CHALLENGE));

        if (this instanceof ArcServerPlayer arcServerPlayer) {
            challenges$getChallenge().ifPresent(challengeProgress -> {
                arcServerPlayer.arc$addActionHolder(challengeProgress.getChallenge());
            });
        }
    }
}

package com.daqem.challenges.mixin;

import com.daqem.arc.api.player.ArcPlayer;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.challenge.ChallengeProgress;
import com.daqem.challenges.player.ChallengesServerPlayer;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import java.util.ArrayList;
import java.util.List;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player implements ChallengesServerPlayer {

    @Unique
    private final List<ChallengeProgress> challenges$challenges = new ArrayList<>();

    public ServerPlayerMixin(Level level, BlockPos blockPos, float f, GameProfile gameProfile) {
        super(level, blockPos, f, gameProfile);
    }

    @Unique
    public List<ChallengeProgress> challenges$getChallenges() {
        return challenges$challenges;
    }

    @Unique
    public void challenges$addChallenge(ChallengeProgress challenge) {
        challenges$challenges.add(challenge);
        if (this instanceof ArcPlayer acrPlayer) {
            acrPlayer.arc$addActionHolder(challenge.getChallenge());
        }
    }

    @Override
    public void challenges$addChallenge(Challenge challenge, int progress) {
        challenges$addChallenge(new ChallengeProgress(challenge, progress));
    }

    @Unique
    public void challenges$addChallenge(Challenge challenge) {
        challenges$addChallenge(new ChallengeProgress(challenge));
    }

}

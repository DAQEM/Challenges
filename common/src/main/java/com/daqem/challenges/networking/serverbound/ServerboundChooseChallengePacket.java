package com.daqem.challenges.networking.serverbound;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.networking.ChallengesNetworking;
import com.daqem.challenges.networking.clientbound.ClientboundOpenChallengeScreenPacket;
import com.daqem.challenges.player.ChallengesServerPlayer;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundChooseChallengePacket extends BaseC2SMessage {

    private ResourceLocation challengeLocation;

    public ServerboundChooseChallengePacket(ResourceLocation challengeLocation) {
        this.challengeLocation = challengeLocation;
    }

    public ServerboundChooseChallengePacket(FriendlyByteBuf friendlyByteBuf) {
        challengeLocation = friendlyByteBuf.readResourceLocation();
    }

    @Override
    public MessageType getType() {
        return ChallengesNetworking.SERVERBOUND_SELECT_CHALLENGE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeResourceLocation(challengeLocation);
    }

    @Override
    public void handle(NetworkManager.PacketContext context) {
        Challenges.getPlatform().getChallengeManager().getChallenge(challengeLocation).ifPresent(challenge -> {
            if (context.getPlayer() instanceof ChallengesServerPlayer serverPlayer) {
                serverPlayer.challenges$setChallengeIfNotPresent(challenge);
                serverPlayer.challenges$getChallenge().ifPresent(challengeProgress ->
                        new ClientboundOpenChallengeScreenPacket(challengeProgress, true).sendTo((ServerPlayer) context.getPlayer()));
            }
        });
    }
}

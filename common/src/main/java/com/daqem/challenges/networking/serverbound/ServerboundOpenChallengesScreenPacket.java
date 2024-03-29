package com.daqem.challenges.networking.serverbound;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.networking.ChallengesNetworking;
import com.daqem.challenges.networking.clientbound.ClientboundOpenChallengeScreenPacket;
import com.daqem.challenges.networking.clientbound.ClientboundOpenChallengesSelectionScreenPacket;
import com.daqem.challenges.player.ChallengesServerPlayer;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

public class ServerboundOpenChallengesScreenPacket extends BaseC2SMessage {

    public ServerboundOpenChallengesScreenPacket() {

    }

    public ServerboundOpenChallengesScreenPacket(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public MessageType getType() {
        return ChallengesNetworking.SERVERBOUND_OPEN_CHALLENGES_SCREEN;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        Challenges.debug("Sending open challenges screen packet to server");
    }

    @Override
    public void handle(NetworkManager.PacketContext context) {
        Challenges.debug("Handling open challenges screen packet on server");
        if (context.getPlayer() instanceof ChallengesServerPlayer player) {
            ServerPlayer serverPlayer = (ServerPlayer) context.getPlayer();

            player.challenges$getChallenge().ifPresentOrElse(
                    challenge -> {
                        new ClientboundOpenChallengeScreenPacket(challenge).sendTo(serverPlayer);
                    },
                    () -> {
                        List<Challenge> challenges = player.challenges$getCachedChallenges();
                        if (challenges.size() < 3) {
                            challenges = Challenges.getPlatform().getChallengeManager().getThreeRandomChallenges();
                        }
                        player.challenges$setCachedChallenges(challenges);
                        new ClientboundOpenChallengesSelectionScreenPacket(
                                challenges
                        ).sendTo(serverPlayer);
                    }
            );
        }
    }
}

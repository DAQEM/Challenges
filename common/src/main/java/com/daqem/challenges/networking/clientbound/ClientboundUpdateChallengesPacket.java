package com.daqem.challenges.networking.clientbound;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.data.ChallengesSerializer;
import com.daqem.challenges.networking.ChallengesNetworking;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

public class ClientboundUpdateChallengesPacket extends BaseS2CMessage {

    private final List<Challenge> challenges;

    public ClientboundUpdateChallengesPacket(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public ClientboundUpdateChallengesPacket(FriendlyByteBuf buf) {
        this.challenges = buf.readList(friendlyByteBuf -> new Challenge.Serializer().fromNetwork(friendlyByteBuf));
    }

    @Override
    public MessageType getType() {
        return ChallengesNetworking.CLIENTBOUND_UPDATE_CHALLENGES;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeCollection(challenges, (friendlyByteBuf, challenge) -> new Challenge.Serializer().toNetwork(friendlyByteBuf, challenge));
    }

    @Override
    public void handle(NetworkManager.PacketContext context) {
        Challenges.getPlatform().getChallengeManager().replaceChallenges(challenges);
    }
}

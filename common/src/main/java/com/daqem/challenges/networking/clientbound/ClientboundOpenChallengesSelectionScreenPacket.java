package com.daqem.challenges.networking.clientbound;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.challenge.Difficulty;
import com.daqem.challenges.client.gui.ChallengesSelectionScreen;
import com.daqem.challenges.networking.ChallengesNetworking;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

public class ClientboundOpenChallengesSelectionScreenPacket extends BaseS2CMessage {

    private final List<Challenge> challenges;

    public ClientboundOpenChallengesSelectionScreenPacket(List<Challenge> challenges) {
        this.challenges = challenges;
    }

    public ClientboundOpenChallengesSelectionScreenPacket(FriendlyByteBuf friendlyByteBuf) {
        challenges = friendlyByteBuf.readList(buf -> new Challenge.Serializer().fromNetwork(buf));
    }

    @Override
    public MessageType getType() {
        return ChallengesNetworking.CLIENTBOUND_OPEN_CHALLENGES_SELECTION_SCREEN;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeCollection(challenges, (buf1, challenge) -> new Challenge.Serializer().toNetwork(buf1, challenge));
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void handle(NetworkManager.PacketContext context) {
        Minecraft.getInstance().setScreen(new ChallengesSelectionScreen(
                challenges
        ));
    }
}

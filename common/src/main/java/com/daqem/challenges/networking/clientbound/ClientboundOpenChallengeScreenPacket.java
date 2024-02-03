package com.daqem.challenges.networking.clientbound;

import com.daqem.challenges.challenge.ChallengeProgress;
import com.daqem.challenges.client.gui.ChallengeScreen;
import com.daqem.challenges.networking.ChallengesNetworking;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

public class ClientboundOpenChallengeScreenPacket extends BaseS2CMessage {

    private final ChallengeProgress challengeProgress;
    private final boolean fadeIn;

    public ClientboundOpenChallengeScreenPacket(ChallengeProgress challengeProgress) {
        this(challengeProgress, false);
    }
    public ClientboundOpenChallengeScreenPacket(ChallengeProgress challengeProgress, boolean fadeIn) {
        this.challengeProgress = challengeProgress;
        this.fadeIn = fadeIn;
    }

    public ClientboundOpenChallengeScreenPacket(FriendlyByteBuf friendlyByteBuf) {
        challengeProgress = new ChallengeProgress.Serializer().fromNetwork(friendlyByteBuf);
        fadeIn = friendlyByteBuf.readBoolean();
    }

    @Override
    public MessageType getType() {
        return ChallengesNetworking.CLIENTBOUND_OPEN_CHALLENGE_SCREEN;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        new ChallengeProgress.Serializer().toNetwork(buf, challengeProgress);
        buf.writeBoolean(fadeIn);
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void handle(NetworkManager.PacketContext context) {
        Minecraft.getInstance().setScreen(new ChallengeScreen(challengeProgress, fadeIn));
    }
}

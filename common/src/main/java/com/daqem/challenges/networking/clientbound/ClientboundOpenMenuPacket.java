package com.daqem.challenges.networking.clientbound;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.challenge.Difficulty;
import com.daqem.challenges.client.gui.ChallengesScreen;
import com.daqem.challenges.networking.ChallengesNetworking;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import java.util.List;

public class ClientboundOpenMenuPacket extends BaseS2CMessage {

    public ClientboundOpenMenuPacket() {
    }

    public ClientboundOpenMenuPacket(FriendlyByteBuf friendlyByteBuf) {
    }

    @Override
    public MessageType getType() {
        return ChallengesNetworking.CLIENTBOUND_OPEN_MENU;
    }

    @Override
    public void write(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(NetworkManager.PacketContext context) {
        Minecraft.getInstance().setScreen(new ChallengesScreen(
                List.of(
                        new Challenge(Challenges.getId("test"), 10, Difficulty.EASY),
                        new Challenge(Challenges.getId("test2"), 10, Difficulty.MEDIUM),
                        new Challenge(Challenges.getId("test3"), 10, Difficulty.HARD)
                )
        ));
    }
}

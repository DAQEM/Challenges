package com.daqem.challenges.networking.clientbound;

import com.daqem.challenges.client.screen.ChallengesScreen;
import com.daqem.challenges.networking.ChallengesNetworking;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseS2CMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

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
        Minecraft.getInstance().setScreen(new ChallengesScreen());
    }
}
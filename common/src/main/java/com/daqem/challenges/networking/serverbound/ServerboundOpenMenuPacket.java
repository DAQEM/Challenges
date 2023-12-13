package com.daqem.challenges.networking.serverbound;

import com.daqem.challenges.networking.ChallengesNetworking;
import com.daqem.challenges.networking.clientbound.ClientboundOpenMenuPacket;
import dev.architectury.networking.NetworkManager;
import dev.architectury.networking.simple.BaseC2SMessage;
import dev.architectury.networking.simple.MessageType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class ServerboundOpenMenuPacket extends BaseC2SMessage {

    public ServerboundOpenMenuPacket() {

    }

    public ServerboundOpenMenuPacket(FriendlyByteBuf friendlyByteBuf) {

    }

    @Override
    public MessageType getType() {
        return ChallengesNetworking.SERVERBOUND_OPEN_MENU;
    }

    @Override
    public void write(FriendlyByteBuf buf) {

    }

    @Override
    public void handle(NetworkManager.PacketContext context) {
        new ClientboundOpenMenuPacket().sendTo((ServerPlayer) context.getPlayer());
    }
}

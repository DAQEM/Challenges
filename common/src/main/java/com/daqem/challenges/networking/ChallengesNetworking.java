package com.daqem.challenges.networking;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.networking.clientbound.ClientboundOpenMenuPacket;
import com.daqem.challenges.networking.serverbound.ServerboundOpenMenuPacket;
import dev.architectury.networking.simple.MessageType;
import dev.architectury.networking.simple.SimpleNetworkManager;

public interface ChallengesNetworking {

    SimpleNetworkManager NETWORK_MANAGER = SimpleNetworkManager.create(Challenges.MOD_ID);

    MessageType SERVERBOUND_OPEN_MENU = NETWORK_MANAGER.registerC2S("serverbound_open_menu", ServerboundOpenMenuPacket::new);

    MessageType CLIENTBOUND_OPEN_MENU = NETWORK_MANAGER.registerS2C("clientbound_open_menu", ClientboundOpenMenuPacket::new);

    static void init() {
    }
}

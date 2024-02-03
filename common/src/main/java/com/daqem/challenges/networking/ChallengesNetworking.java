package com.daqem.challenges.networking;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.networking.clientbound.ClientboundOpenChallengeScreenPacket;
import com.daqem.challenges.networking.clientbound.ClientboundOpenChallengesSelectionScreenPacket;
import com.daqem.challenges.networking.clientbound.ClientboundUpdateChallengesPacket;
import com.daqem.challenges.networking.serverbound.ServerboundChooseChallengePacket;
import com.daqem.challenges.networking.serverbound.ServerboundOpenChallengesScreenPacket;
import dev.architectury.networking.simple.MessageType;
import dev.architectury.networking.simple.SimpleNetworkManager;

public interface ChallengesNetworking {

    SimpleNetworkManager NETWORK_MANAGER = SimpleNetworkManager.create(Challenges.MOD_ID);

    MessageType SERVERBOUND_OPEN_CHALLENGES_SCREEN = NETWORK_MANAGER.registerC2S("serverbound_open_challenges_screen", ServerboundOpenChallengesScreenPacket::new);
    MessageType SERVERBOUND_SELECT_CHALLENGE = NETWORK_MANAGER.registerC2S("serverbound_select_challenge", ServerboundChooseChallengePacket::new);

    MessageType CLIENTBOUND_OPEN_CHALLENGES_SELECTION_SCREEN = NETWORK_MANAGER.registerS2C("clientbound_open_challenges_selection_screen", ClientboundOpenChallengesSelectionScreenPacket::new);
    MessageType CLIENTBOUND_OPEN_CHALLENGE_SCREEN = NETWORK_MANAGER.registerS2C("clientbound_open_challenge_screen", ClientboundOpenChallengeScreenPacket::new);
    MessageType CLIENTBOUND_UPDATE_CHALLENGES = NETWORK_MANAGER.registerS2C("clientbound_update_challenges", ClientboundUpdateChallengesPacket::new);

    static void init() {
    }
}

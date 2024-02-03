package com.daqem.challenges.event;

import com.daqem.challenges.player.ChallengesServerPlayer;
import dev.architectury.event.events.common.PlayerEvent;
import net.minecraft.server.dedicated.DedicatedServer;

public class PlayerJoinEvent {

    public static void registerEvent() {
        PlayerEvent.PLAYER_JOIN.register(player -> {
            if (player instanceof ChallengesServerPlayer serverPlayer) {
                if (player.getServer() instanceof DedicatedServer) {
                    serverPlayer.challenges$syncChallenges();
                }
            }
        });
    }
}

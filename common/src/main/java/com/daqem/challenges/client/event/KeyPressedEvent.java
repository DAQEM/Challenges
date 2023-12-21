package com.daqem.challenges.client.event;

import com.daqem.challenges.client.ChallengesClient;
import com.daqem.challenges.client.gui.ChallengesScreen;
import com.daqem.challenges.networking.serverbound.ServerboundOpenMenuPacket;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import net.minecraft.client.gui.screens.Screen;

public class KeyPressedEvent {

    public static void registerEvent() {
        ClientRawInputEvent.KEY_PRESSED.register((client, keyCode, scanCode, action, modifiers) -> {
            Screen screen = client.screen;
            if (ChallengesClient.OPEN_MENU.matches(keyCode, scanCode) && action == 1) {
                if (screen instanceof ChallengesScreen) screen.onClose();
                else if (screen == null) new ServerboundOpenMenuPacket().sendToServer();
            }
            return EventResult.pass();
        });
    }
}

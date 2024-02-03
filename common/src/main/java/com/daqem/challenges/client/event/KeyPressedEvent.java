package com.daqem.challenges.client.event;

import com.daqem.challenges.client.ChallengesClient;
import com.daqem.challenges.client.gui.ChallengesSelectionScreen;
import com.daqem.challenges.networking.serverbound.ServerboundOpenChallengesScreenPacket;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import net.minecraft.client.gui.screens.Screen;

public class KeyPressedEvent {

    public static void registerEvent() {
        ClientRawInputEvent.KEY_PRESSED.register((client, keyCode, scanCode, action, modifiers) -> {
            Screen screen = client.screen;
            if (ChallengesClient.OPEN_MENU.matches(keyCode, scanCode) && action == 1) {
                if (screen instanceof ChallengesSelectionScreen) screen.onClose();
                else if (screen == null) new ServerboundOpenChallengesScreenPacket().sendToServer();
            }
            return EventResult.pass();
        });
    }
}

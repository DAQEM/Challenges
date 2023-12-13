package com.daqem.challenges.event;

import com.daqem.challenges.command.ChallengesCommand;
import dev.architectury.event.events.common.CommandRegistrationEvent;

public class RegisterCommandsEvent {

    public static void registerEvent() {
        CommandRegistrationEvent.EVENT.register((dispatcher, registry, selection) -> {
            ChallengesCommand.registerCommand(dispatcher);
        });


    }
}

package com.daqem.challenges.holder;

import com.daqem.arc.api.action.holder.IActionHolder;
import com.daqem.arc.api.action.holder.type.ActionHolderType;
import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;

public interface ChallengesActionHolderType<T extends IActionHolder> extends ActionHolderType<T> {

    ActionHolderType<Challenge> CHALLENGE = ActionHolderType.register(Challenges.getId("challenge"));

    static void init() {
    }
}

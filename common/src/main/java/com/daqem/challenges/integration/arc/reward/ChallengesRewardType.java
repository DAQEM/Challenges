package com.daqem.challenges.integration.arc.reward;

import com.daqem.arc.api.reward.IReward;
import com.daqem.arc.api.reward.type.IRewardType;
import com.daqem.arc.api.reward.type.RewardType;
import com.daqem.challenges.Challenges;

public interface ChallengesRewardType<T extends IReward> extends RewardType<T> {

    IRewardType<ChallengeOccurrenceReward> CHALLENGE_OCCURRENCE = RewardType.register(Challenges.getId("challenge_occurrence"));

    static void init() {
    }
}

package com.daqem.challenges.integration.arc.reward;

import com.daqem.arc.api.reward.IReward;
import com.daqem.arc.api.reward.serializer.IRewardSerializer;
import com.daqem.arc.api.reward.serializer.RewardSerializer;
import com.daqem.challenges.Challenges;

public interface ChallengesRewardSerializer<T extends IReward> extends RewardSerializer<T> {

    IRewardSerializer<ChallengeOccurrenceReward> CHALLENGE_OCCURRENCE = RewardSerializer.register(Challenges.getId("challenge_occurrence"), new ChallengeOccurrenceReward.Serializer());

    static void init() {
    }
}

package com.daqem.challenges.player;

import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.challenge.ChallengeProgress;

import java.util.Optional;

public interface ChallengesServerPlayer extends ChallengesPlayer {

    Optional<ChallengeProgress> challenges$getChallenge();
    void challenges$setChallenge(Challenge challenge);
    void challenges$setChallengeIfNotPresent(Challenge challenge);
    void challenges$setChallengeProgress(int progress);
    void challenges$setChallenge(ChallengeProgress challenge);
    void challenges$removeChallenge();
}

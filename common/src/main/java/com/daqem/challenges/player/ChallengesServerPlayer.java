package com.daqem.challenges.player;

import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.challenge.ChallengeProgress;

import java.util.List;

public interface ChallengesServerPlayer extends ChallengesPlayer {

    List<ChallengeProgress> challenges$getChallenges();
    void challenges$addChallenge(Challenge challenge);
    void challenges$addChallenge(Challenge challenge, int progress);
    void challenges$addChallenge(ChallengeProgress challenge);
}

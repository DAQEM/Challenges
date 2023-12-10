package com.daqem.challenges.challenge;

import net.minecraft.util.Mth;

public class ChallengeProgress {

    private final Challenge challenge;
    private int progress;

    public ChallengeProgress(Challenge challenge) {
        this(challenge, 0);
    }

    public ChallengeProgress(Challenge challenge, int progress) {
        this.challenge = challenge;
        this.progress = progress;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public int getProgress() {
        return progress;
    }

    public double getPercentComplete() {
        return Mth.clamp((double) progress / challenge.getGoal(), 0, 1);
    }

    public void incrementProgress() {
        incrementProgress(1);
    }

    public void incrementProgress(int amount) {
        progress += amount;
    }

    public boolean isComplete() {
        return progress >= challenge.getGoal();
    }
}

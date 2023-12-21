package com.daqem.challenges.challenge;

import com.daqem.challenges.Challenges;
import net.minecraft.network.chat.Component;

public enum Difficulty {

    EASY(1),
    MEDIUM(2),
    HARD(3),
    EXTREME(4),
    IMPOSSIBLE(5);

    private final int id;

    Difficulty(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Difficulty getById(int id) {
        for (Difficulty difficulty : values()) {
            if (difficulty.getId() == id) {
                return difficulty;
            }
        }
        return null;
    }

    public Component getDisplayName() {
        return Challenges.translatable("difficulty." + name().toLowerCase());
    }
}

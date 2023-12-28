package com.daqem.challenges.challenge;

import com.daqem.challenges.Challenges;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public enum Difficulty {

    EASY(1, 0x3c9484),
    MEDIUM(2, 0xeead40),
    HARD(3, 0xec4b44);

    private final int id;
    private final int color;

    Difficulty(int id, int color) {
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public int getColor() {
        return color;
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

    public Component getLowercaseDisplayNameWithColor() {
        return Challenges.literal(getDisplayName().getString().toLowerCase()).withStyle(Style.EMPTY.withColor(color));
    }
}

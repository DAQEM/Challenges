package com.daqem.challenges.client.screen;

import com.daqem.challenges.Challenges;
import com.daqem.uilib.client.gui.AbstractScreen;
import com.daqem.uilib.client.gui.background.Backgrounds;
import net.minecraft.client.gui.GuiGraphics;

public class ChallengesScreen extends AbstractScreen {

    public ChallengesScreen() {
        super(Challenges.translatable("screen.challenges.title"));
    }

    @Override
    public void startScreen() {
        setBackground(Backgrounds.getDefaultBackground(width, height));
    }

    @Override
    public void onTickScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
    }
}

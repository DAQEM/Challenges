package com.daqem.challenges.client.gui;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.ChallengeProgress;
import com.daqem.challenges.client.gui.component.ChallengeBookComponent;
import com.daqem.challenges.client.gui.component.ChallengeCardComponent;
import com.daqem.uilib.client.gui.AbstractScreen;
import com.daqem.uilib.client.gui.background.Backgrounds;
import com.daqem.uilib.client.gui.component.TextComponent;
import com.daqem.uilib.client.gui.component.TextureComponent;
import com.daqem.uilib.client.gui.text.Text;
import com.daqem.uilib.client.gui.texture.Texture;
import net.minecraft.client.gui.GuiGraphics;

public class ChallengeScreen extends AbstractScreen {

    private static final int FADE_IN_COOLDOWN = 10;

    private final ChallengeProgress challengeProgress;

    private ChallengeCardComponent challengeCardComponent;
    private TextureComponent bookComponent;

    private final boolean fadeIn;
    private int fadeInCooldown = 0;
    private boolean isCardShown = false;
    private int bookOffsetXGoal = 0;
    private int bookOffsetYGoal = 0;
    private int cardOffsetXGoal = 0;
    private int cardOffsetYGoal = 0;
    private int bookOffsetX = 0;
    private int bookOffsetY = 0;
    private int cardOffsetX = 0;
    private int cardOffsetY = 0;

    public ChallengeScreen(ChallengeProgress challengeProgress, boolean fadeIn) {
        super(Challenges.translatable("screen.challenge.title"));
        this.challengeProgress = challengeProgress;
        this.fadeIn = fadeIn;
        if (this.fadeIn) {
            bookOffsetY = -260;
        }
    }

    @Override
    public void startScreen() {
        setBackground(Backgrounds.getDefaultBackground(width, height));

        challengeCardComponent = new ChallengeCardComponent(0, 0, challengeProgress.getChallenge(), font);
        bookComponent = new ChallengeBookComponent(font, challengeProgress);

        challengeCardComponent.setScale(.8F);
        challengeCardComponent.setOnClickEvent((clickedObject, screen, mouseX, mouseY, button) -> {
            if (button == 0) {
                isCardShown = !isCardShown;
                if (isCardShown) {
                    bookOffsetXGoal = -60;
                    cardOffsetXGoal = 80;
                    cardOffsetYGoal = 45;
                } else {
                    bookOffsetXGoal = 0;
                    cardOffsetXGoal = 0;
                    cardOffsetYGoal = 0;
                }
            }
        });

        addComponents(challengeCardComponent, bookComponent);

        positionComponents();
    }

    @Override
    public void onResizeScreenRepositionComponents(int width, int height) {
        super.onResizeScreenRepositionComponents(width, height);
    }

    private void positionComponents() {
        bookComponent.center();
        challengeCardComponent.center();

        bookComponent.setX(bookComponent.getX() + bookOffsetX);
        bookComponent.setY(bookComponent.getY() + bookOffsetY);
        challengeCardComponent.setX(challengeCardComponent.getX() + (bookComponent.getWidth() / 4) - 4 + cardOffsetX);
        challengeCardComponent.setY(challengeCardComponent.getY() - 42 + cardOffsetY);
    }

    @Override
    public void onTickScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        positionComponents();

        if (fadeInCooldown == FADE_IN_COOLDOWN) {
            if (bookOffsetYGoal > bookOffsetY) {
                bookOffsetY += 5;
            } else if (bookOffsetYGoal < bookOffsetY) {
                bookOffsetY -= 5;
            }
        } else {
            fadeInCooldown++;
        }

        if (bookOffsetXGoal > bookOffsetX) {
            bookOffsetX += 5;
        } else if (bookOffsetXGoal < bookOffsetX) {
            bookOffsetX -= 5;
        }

        if (cardOffsetY == cardOffsetYGoal && cardOffsetXGoal > cardOffsetX) {
            cardOffsetX += 5;
        } else if (cardOffsetXGoal < cardOffsetX) {
            cardOffsetX -= 5;
        }

        if (cardOffsetYGoal > cardOffsetY) {
            cardOffsetY += 5;
        } else if (cardOffsetX == cardOffsetXGoal && cardOffsetYGoal < cardOffsetY) {
            cardOffsetY -= 5;
        }
    }
}

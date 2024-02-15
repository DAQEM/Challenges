package com.daqem.challenges.client.gui;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.ChallengeProgress;
import com.daqem.challenges.client.gui.component.BookComponent;
import com.daqem.challenges.client.gui.component.CardComponent;
import com.daqem.uilib.client.gui.AbstractScreen;
import com.daqem.uilib.client.gui.background.Backgrounds;
import com.daqem.uilib.client.gui.component.TextureComponent;
import net.minecraft.client.gui.GuiGraphics;

public class ChallengeScreen extends AbstractScreen {

    private static final int FADE_IN_COOLDOWN = 10;

    private final ChallengeProgress challengeProgress;

    private CardComponent cardComponent;
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
        setBackground(Backgrounds.getDefaultBackground(getWidth(), getHeight()));

        cardComponent = new CardComponent(0, 0, challengeProgress.getChallenge(), getFont());
        bookComponent = new BookComponent(getFont(), challengeProgress);

        cardComponent.setScale(.8F);
        cardComponent.setOnClickEvent((clickedObject, screen, mouseX, mouseY, button) -> {
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

        addComponents(cardComponent, bookComponent);

        positionComponents();
    }

    @Override
    public void onResizeScreenRepositionComponents(int width, int height) {
        super.onResizeScreenRepositionComponents(width, height);
    }

    private void positionComponents() {
        bookComponent.center();
        cardComponent.center();

        bookComponent.setX(bookComponent.getX() + bookOffsetX);
        bookComponent.setY(bookComponent.getY() + bookOffsetY);
        cardComponent.setX(cardComponent.getX() + (bookComponent.getWidth() / 4) - 4 + cardOffsetX);
        cardComponent.setY(cardComponent.getY() - 42 + cardOffsetY);
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

package com.daqem.challenges.client.gui;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.client.gui.component.CardComponent;
import com.daqem.challenges.networking.serverbound.ServerboundChooseChallengePacket;
import com.daqem.uilib.client.gui.AbstractScreen;
import com.daqem.uilib.client.gui.background.Backgrounds;
import com.daqem.uilib.client.gui.component.TextComponent;
import com.daqem.uilib.client.gui.text.Text;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.List;

public class ChallengesSelectionScreen extends AbstractScreen {

    private final static Component TITLE = Challenges.translatable("screen.challenges_selection.title");
    private final static Component SUBTITLE = Challenges.translatable("screen.challenges_selection.subtitle");

    private final List<Challenge> challenges;
    private final List<CardComponent> cardComponents = new ArrayList<>();

    private TextComponent titleComponent;
    private TextComponent subtitleComponent;

    private CardComponent selectedChallenge = null;
    private int selectedChallengeOffsetXGoal = 0;
    private int selectedChallengeOffsetYGoal = 0;
    private int unselectedChallengeOffsetYGoal = 0;
    private int selectedChallengeOffsetX = 0;
    private int selectedChallengeOffsetY = 0;
    private int unselectedChallengeOffsetY = 0;

    public ChallengesSelectionScreen(List<Challenge> challenges) {
        super(TITLE);
        this.challenges = challenges;
    }

    @Override
    public void startScreen() {
        setBackground(Backgrounds.getDefaultBackground(width, height));

        this.titleComponent = new TextComponent(0, 0, new Text(this.font, TITLE, 0, 0));
        this.subtitleComponent = new TextComponent(0, 0, new Text(this.font, SUBTITLE, 0, 0));

        titleComponent.setScale(1.5F);
        titleComponent.setZ(-1);
        subtitleComponent.setZ(-1);
        if (subtitleComponent.getText() != null) {
            subtitleComponent.getText().setTextColor(ChatFormatting.GRAY);
        }

        addComponents(titleComponent, subtitleComponent);

        for (Challenge challenge : challenges) {
            CardComponent card = new CardComponent(0, 0, challenge, this.font);
            card.setOnClickEvent((clickedObject, screen, mouseX, mouseY, button) -> {
                if (button == 0) {
                    if (selectedChallenge == null) {
                        selectedChallenge = card;
                        selectedChallengeOffsetYGoal = -50;
                        selectedChallengeOffsetXGoal = 70;
                        unselectedChallengeOffsetYGoal = 220;
                    }
//                    new ServerboundChooseChallengePacket(challenge.getLocation()).sendToServer();
                }
            });
            cardComponents.add(card);
            addComponents(card);
        }
    }

    private void positionComponents() {

        for (int i = -1; i < challenges.size() - 1; i++) {
            CardComponent card = this.cardComponents.get(i + 1);
            card.setScale(.8F);
            card.center();
            card.setX(card.getX() + (110 * i));
            card.setY(card.getY() + 10);
        }

        titleComponent.center();
        titleComponent.setY(titleComponent.getY() - 100);
        titleComponent.setOpacity((selectedChallengeOffsetY + 100F) / 100F);

        subtitleComponent.center();
        subtitleComponent.setY(subtitleComponent.getY() - 88);
        subtitleComponent.setOpacity((selectedChallengeOffsetY + 100F) / 100F);

        if (selectedChallenge != null) {
            for (CardComponent cardComponent : cardComponents) {
                if (cardComponent != selectedChallenge) {
                    cardComponent.setY(cardComponent.getY() + unselectedChallengeOffsetY);
                } else {
                    cardComponent.setX(cardComponent.getX() + selectedChallengeOffsetX);
                    cardComponent.setY(cardComponent.getY() + selectedChallengeOffsetY);
                }
            }
        }
    }

    @Override
    public void onTickScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        positionComponents();

        if (selectedChallengeOffsetXGoal > selectedChallengeOffsetX) {
            selectedChallengeOffsetX += 5;
        } else if (selectedChallengeOffsetXGoal < selectedChallengeOffsetX) {
            selectedChallengeOffsetX -= 5;
        }

        if (selectedChallengeOffsetYGoal > selectedChallengeOffsetY) {
            selectedChallengeOffsetY += 5;
        } else if (selectedChallengeOffsetYGoal < selectedChallengeOffsetY) {
            selectedChallengeOffsetY -= 5;
        }

        if (unselectedChallengeOffsetYGoal > unselectedChallengeOffsetY) {
            unselectedChallengeOffsetY += 8;
        } else if (unselectedChallengeOffsetYGoal < unselectedChallengeOffsetY) {
            unselectedChallengeOffsetY -= 8;
        }

        if (selectedChallengeOffsetX == selectedChallengeOffsetXGoal && selectedChallengeOffsetY == selectedChallengeOffsetYGoal && selectedChallengeOffsetXGoal != 0 && selectedChallengeOffsetYGoal != 0) {
            new ServerboundChooseChallengePacket(selectedChallenge.getChallenge().getLocation()).sendToServer();
        }
    }
}

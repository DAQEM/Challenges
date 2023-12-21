package com.daqem.challenges.client.gui;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.client.gui.component.CardComponent;
import com.daqem.challenges.client.gui.component.ChallengeComponent;
import com.daqem.uilib.client.gui.AbstractScreen;
import com.daqem.uilib.client.gui.background.Backgrounds;
import com.daqem.uilib.client.gui.component.TextComponent;
import com.daqem.uilib.client.gui.text.Text;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class ChallengesScreen extends AbstractScreen {

    private List<Challenge> challenges;
    private List<ChallengeComponent> challengeComponents;
    private TextComponent titleComponent;
    private TextComponent subtitleComponent;

    public ChallengesScreen(List<Challenge> challenges) {
        super(Challenges.translatable("screen.challenges.title"));
        this.challenges = challenges;
    }

    @Override
    public void startScreen() {
        setBackground(Backgrounds.getDefaultBackground(width, height));

        for (int i = -1; i < challenges.size() - 1; i++) {
            Challenge challenge = challenges.get(i + 1);
            CardComponent cardComponent = new CardComponent(0, 0, challenge);
            cardComponent.setScale(0.75F);
            cardComponent.center();
            cardComponent.setX(cardComponent.getX() + (100 * i));
            addComponents(cardComponent);
        }

        this.titleComponent = new TextComponent(0, 0, new Text(this.font, Challenges.translatable("screen.challenges.title"), 0, 0));
        this.subtitleComponent = new TextComponent(0, 0, new Text(this.font, Challenges.translatable("screen.challenges.subtitle"), 0, 0));
        this.challengeComponents = challenges.stream().map(x -> new ChallengeComponent(this.font, x)).toList();

        titleComponent.setScale(1.5F);
        if (subtitleComponent.getText() != null) {
            subtitleComponent.getText().setTextColor(ChatFormatting.GRAY);
        }
        subtitleComponent.setScale(0.75F);

        addComponents(titleComponent, subtitleComponent);
//        addComponents(challengeComponents.toArray(new ChallengeComponent[0]));

        positionComponents();
    }

    @Override
    public void onResizeScreenRepositionComponents(int width, int height) {
        super.onResizeScreenRepositionComponents(width, height);
        positionComponents();
    }

    private void positionComponents() {
        titleComponent.center();
        titleComponent.setY(titleComponent.getY() - 100);

        subtitleComponent.center();
        subtitleComponent.setY(subtitleComponent.getY() - 88);

        for (int i = -1; i < this.challengeComponents.size() - 1; i++) {
            ChallengeComponent challengeComponent = this.challengeComponents.get(i + 1);
            challengeComponent.center();
            challengeComponent.setY(challengeComponent.getY() + 10);
            challengeComponent.setX(challengeComponent.getX() + (i * (challengeComponent.getWidth() + 10)) + 4);
        }
    }

    @Override
    public void onTickScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
    }
}

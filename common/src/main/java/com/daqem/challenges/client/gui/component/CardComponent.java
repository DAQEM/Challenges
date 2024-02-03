package com.daqem.challenges.client.gui.component;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.challenge.Difficulty;
import com.daqem.challenges.config.ChallengesConfig;
import com.daqem.uilib.client.gui.component.*;
import com.daqem.uilib.client.gui.component.TextComponent;
import com.daqem.uilib.client.gui.text.Text;
import com.daqem.uilib.client.gui.text.TruncatedText;
import com.daqem.uilib.client.gui.text.multiline.MultiLineText;
import com.daqem.uilib.client.gui.texture.Texture;
import com.daqem.uilib.client.gui.texture.Textures;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class CardComponent extends TextureComponent {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 174;

    private final Challenge challenge;
    private Font font;

    private TextComponent difficultyComponent;
    private TextComponent nameComponent;
    private TextComponent descriptionComponent;
    private NineSlicedTextureComponent nameBackgroundComponent;
    private ImageComponent imageComponent;

    public CardComponent(int x, int y, Challenge challenge, Font font) {
        super(new Texture(Challenges.getId("textures/gui/card.png"), 0, 0, 120, 174), x, y, WIDTH, HEIGHT);
        this.challenge = challenge;
        this.font = font;
    }

    @Override
    public void startRenderable() {

        difficultyComponent = new TextComponent(0, 5, new Text(font, challenge.getDifficulty().getDisplayName(), 0, 0, Math.min(88, font.width(challenge.getDifficulty().getDisplayName())), 9));
        nameComponent = new TextComponent(0, 78, new TruncatedText(font, challenge.getName(), 0, 0, Math.min(98, font.width(challenge.getName())), 9));
        nameBackgroundComponent = new NineSlicedTextureComponent(Textures.Advancement.ADVANCEMENT_HOVER_BAR_OBTAINED, -3, 72, 106, 20);
        descriptionComponent = new TextComponent(10, 108, new MultiLineText(font, challenge.getDescription(), 0, 0, 106));
        TextureComponent imageComponent = new TextureComponent(new Texture(challenge.getImageLocation(), 0, 0, 104 * 2, 104 * 2), 8, 11, 104, 104);

        imageComponent.setZ(-1);

        addChildren(imageComponent, descriptionComponent);


        Component name = challenge.getName();
        TextComponent nameComponent = new TextComponent(10, 12, new TruncatedText(font, name, 0, 0, Math.min(100, font.width(name)), 9));
        addChildren(nameComponent);

        int x = 105;
        int y = 53;

        TextComponent threeComponent = new TextComponent(x, y, new Text(font, Component.literal("3"), 0, 0, 10, 9));
        TextComponent twoComponent = new TextComponent(x, y + 10, new Text(font, Component.literal("2"), 0, 0, 10, 9));
        TextComponent oneComponent = new TextComponent(x,  y + 20, new Text(font, Component.literal("1"), 0, 0, 10, 9));

        threeComponent.getText().setBold(true);
        twoComponent.getText().setBold(true);
        oneComponent.getText().setBold(true);

        if (challenge.getDifficulty() == Difficulty.HARD) {
            oneComponent.getText().setTextColor(ChatFormatting.GRAY);
            twoComponent.getText().setTextColor(ChatFormatting.GRAY);
        } else if (challenge.getDifficulty() == Difficulty.MEDIUM) {
            oneComponent.getText().setTextColor(ChatFormatting.GRAY);
            threeComponent.getText().setTextColor(ChatFormatting.GRAY);
        } else if (challenge.getDifficulty() == Difficulty.EASY) {
            twoComponent.getText().setTextColor(ChatFormatting.GRAY);
            threeComponent.getText().setTextColor(ChatFormatting.GRAY);
        }


        addChildren(oneComponent, twoComponent, threeComponent);

        positionComponents();

        super.startRenderable();
    }

    @Override
    public void resizeScreenRepositionRenderable(int width, int height) {
        super.resizeScreenRepositionRenderable(width, height);
        positionComponents();
    }

    private void positionComponents() {
        difficultyComponent.centerHorizontally();
        nameComponent.centerHorizontally();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);

        int color;

        if (challenge.getDifficulty() == Difficulty.HARD) {
            color = ChallengesConfig.hardColor.get() + 0xFF000000;
            graphics.hLine(12, 15, 52, color);
            graphics.hLine(12, 15, 53, color);
            graphics.hLine(10, 17, 54, color);
            graphics.hLine(10, 17, 55, color);
            graphics.hLine(10, 17, 56, color);
            graphics.hLine(10, 17, 57, color);
            graphics.hLine(12, 15, 58, color);
            graphics.hLine(12, 15, 59, color);

        } else if (challenge.getDifficulty() == Difficulty.MEDIUM) {
            color = ChallengesConfig.mediumColor.get() + 0xFF000000;
            graphics.hLine(12, 15, 62, color);
            graphics.hLine(12, 15, 63, color);
            graphics.hLine(10, 17, 64, color);
            graphics.hLine(10, 17, 65, color);
            graphics.hLine(10, 17, 66, color);
            graphics.hLine(10, 17, 67, color);
            graphics.hLine(12, 15, 68, color);
            graphics.hLine(12, 15, 69, color);


        } else if (challenge.getDifficulty() == Difficulty.EASY) {
            color = ChallengesConfig.easyColor.get() + 0xFF000000;
            graphics.hLine(12, 15, 72, color);
            graphics.hLine(12, 15, 73, color);
            graphics.hLine(10, 17, 74, color);
            graphics.hLine(10, 17, 75, color);
            graphics.hLine(10, 17, 76, color);
            graphics.hLine(10, 17, 77, color);
            graphics.hLine(12, 15, 78, color);
            graphics.hLine(12, 15, 79, color);
        }
    }

    public Challenge getChallenge() {
        return challenge;
    }
}

package com.daqem.challenges.client.gui.component;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.challenge.Difficulty;
import com.daqem.challenges.networking.serverbound.ServerboundChooseChallengePacket;
import com.daqem.uilib.api.client.gui.texture.ITexture;
import com.daqem.uilib.client.gui.color.ColorManipulator;
import com.daqem.uilib.client.gui.component.*;
import com.daqem.uilib.client.gui.component.TextComponent;
import com.daqem.uilib.client.gui.text.Text;
import com.daqem.uilib.client.gui.text.TruncatedText;
import com.daqem.uilib.client.gui.text.multiline.MultiLineText;
import com.daqem.uilib.client.gui.texture.Texture;
import com.daqem.uilib.client.gui.texture.Textures;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.Objects;

public class ChallengeCardComponent extends TextureComponent {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 174;
    private static final float DESCRIPTION_SCALE = .8F;

    private final Challenge challenge;
    private Font font;

    private TextComponent difficultyComponent;
    private TextComponent nameComponent;
    private TextComponent descriptionComponent;
    private NineSlicedTextureComponent nameBackgroundComponent;
    private ImageComponent imageComponent;

    public ChallengeCardComponent(int x, int y, Challenge challenge, Font font) {
        super(new Texture(Challenges.getId("textures/gui/test-card.png"), 0, 0, 120, 174), x, y, WIDTH, HEIGHT);
        this.challenge = challenge;
        this.font = font;
    }

    @Override
    public void startRenderable() {
//        setColorManipulator(new ColorManipulator(.45F, .285F, .175F));

        difficultyComponent = new TextComponent(0, 5, new Text(font, challenge.getDifficulty().getDisplayName(), 0, 0, Math.min(88, font.width(challenge.getDifficulty().getDisplayName())), 9));
        nameComponent = new TextComponent(0, 78, new TruncatedText(font, challenge.getName(), 0, 0, Math.min(98, font.width(challenge.getName())), 9));
        nameBackgroundComponent = new NineSlicedTextureComponent(Textures.Advancement.ADVANCEMENT_HOVER_BAR_OBTAINED, -3, 72, 106, 20);
        descriptionComponent = new TextComponent(10, 108, new MultiLineText(font, challenge.getDescription(), 0, 0, 106));
        TextureComponent imageComponent = new TextureComponent(new Texture(challenge.getImageLocation(), 0, 0, 104 * 2, 104 * 2), 8, 11, 104, 104);

        imageComponent.setZ(-1);

//        descriptionComponent.setScale(DESCRIPTION_SCALE);

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
//        graphics.fill(5, 14, 90 + 5, 62 + 14, 0xff000000);

        int gray = 0xff5a524c;

        if (challenge.getDifficulty() == Difficulty.HARD) {
            graphics.hLine(12, 15, 62, gray);
            graphics.hLine(12, 15, 63, gray);
            graphics.hLine(10, 17, 64, gray);
            graphics.hLine(10, 17, 65, gray);
            graphics.hLine(10, 17, 66, gray);
            graphics.hLine(10, 17, 67, gray);
            graphics.hLine(12, 15, 68, gray);
            graphics.hLine(12, 15, 69, gray);

            graphics.hLine(12, 15, 72, gray);
            graphics.hLine(12, 15, 73, gray);
            graphics.hLine(10, 17, 74, gray);
            graphics.hLine(10, 17, 75, gray);
            graphics.hLine(10, 17, 76, gray);
            graphics.hLine(10, 17, 77, gray);
            graphics.hLine(12, 15, 78, gray);
            graphics.hLine(12, 15, 79, gray);
        } else if (challenge.getDifficulty() == Difficulty.MEDIUM) {
            graphics.hLine(12, 15, 52, gray);
            graphics.hLine(12, 15, 53, gray);
            graphics.hLine(10, 17, 54, gray);
            graphics.hLine(10, 17, 55, gray);
            graphics.hLine(10, 17, 56, gray);
            graphics.hLine(10, 17, 57, gray);
            graphics.hLine(12, 15, 58, gray);
            graphics.hLine(12, 15, 59, gray);

            graphics.hLine(12, 15, 72, gray);
            graphics.hLine(12, 15, 73, gray);
            graphics.hLine(10, 17, 74, gray);
            graphics.hLine(10, 17, 75, gray);
            graphics.hLine(10, 17, 76, gray);
            graphics.hLine(10, 17, 77, gray);
            graphics.hLine(12, 15, 78, gray);
            graphics.hLine(12, 15, 79, gray);
        } else if (challenge.getDifficulty() == Difficulty.EASY) {
            graphics.hLine(12, 15, 52, gray);
            graphics.hLine(12, 15, 53, gray);
            graphics.hLine(10, 17, 54, gray);
            graphics.hLine(10, 17, 55, gray);
            graphics.hLine(10, 17, 56, gray);
            graphics.hLine(10, 17, 57, gray);
            graphics.hLine(12, 15, 58, gray);
            graphics.hLine(12, 15, 59, gray);

            graphics.hLine(12, 15, 62, gray);
            graphics.hLine(12, 15, 63, gray);
            graphics.hLine(10, 17, 64, gray);
            graphics.hLine(10, 17, 65, gray);
            graphics.hLine(10, 17, 66, gray);
            graphics.hLine(10, 17, 67, gray);
            graphics.hLine(12, 15, 68, gray);
            graphics.hLine(12, 15, 69, gray);
        }
    }

    public Challenge getChallenge() {
        return challenge;
    }
}

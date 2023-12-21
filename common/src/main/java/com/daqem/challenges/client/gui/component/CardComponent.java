package com.daqem.challenges.client.gui.component;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.uilib.api.client.gui.texture.INineSlicedTexture;
import com.daqem.uilib.api.client.gui.texture.ITexture;
import com.daqem.uilib.client.gui.component.*;
import com.daqem.uilib.client.gui.component.TextComponent;
import com.daqem.uilib.client.gui.text.Text;
import com.daqem.uilib.client.gui.text.TruncatedText;
import com.daqem.uilib.client.gui.text.multiline.MultiLineText;
import com.daqem.uilib.client.gui.texture.NineSlicedTexture;
import com.daqem.uilib.client.gui.texture.Texture;
import com.daqem.uilib.client.gui.texture.Textures;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.awt.*;

public class CardComponent extends NineSlicedTextureComponent {

    private static final int WIDTH = 120;
    private static final int HEIGHT = 180;

    private static final ResourceLocation CARD_LOCATION = Challenges.getId("textures/gui/card.png");
    private static final ITexture CARD_TEXTURE = new Texture(CARD_LOCATION, 0, 0, WIDTH, HEIGHT);

    private final Challenge challenge;

    public CardComponent(int x, int y, Challenge challenge) {
        super(Textures.BUTTON, x, y, WIDTH, HEIGHT);
        this.challenge = challenge;
    }

    @Override
    public void startRenderable() {

        Font font = Minecraft.getInstance().font;
        int difficultyTextMinWidth = Math.min(102, font.width(challenge.getDifficulty().getDisplayName()));
        Text difficultyText = new Text(font, challenge.getDifficulty().getDisplayName(), 0, 0, difficultyTextMinWidth, 9);
        TextComponent difficultyComponent = new TextComponent(0, 6, difficultyText);
        difficultyText.setTextColor(ChatFormatting.DARK_GRAY);

        int nameTextMinWidth = Math.min(102, font.width(challenge.getName()));
        TruncatedText nameText = new TruncatedText(font, challenge.getName(), 0, 0, nameTextMinWidth, 9);
        TextComponent nameComponent = new TextComponent(7, 93, nameText);

        NineSlicedTextureComponent nameBackgroundComponent = new NineSlicedTextureComponent(Textures.Advancement.ADVANCEMENT_HOVER_BAR_OBTAINED, 4, 87, 112, 20);

        float descriptionScale = 0.80F;
        MultiLineText descriptionText = new MultiLineText(font, challenge.getDescription(), 0, 0, (int) (111 / descriptionScale));
        TextComponent descriptionComponent = new TextComponent(7, 106, descriptionText);
        descriptionText.setTextColor(ChatFormatting.DARK_GRAY);
        descriptionComponent.setScale(descriptionScale);

        ImageComponent imageComponent;
        switch (challenge.getDifficulty()) {
            case EASY -> imageComponent = new ImageComponent(Challenges.getId("textures/gui/images/desert.png"), 11, 17);
            case MEDIUM -> imageComponent = new ImageComponent(Challenges.getId("textures/gui/images/river.png"), 11, 17);
            default -> imageComponent = new ImageComponent(Challenges.getId("textures/gui/images/island.png"), 11, 17);
        }

        addChildren(difficultyComponent, imageComponent, nameBackgroundComponent, nameComponent, descriptionComponent);
        difficultyComponent.centerHorizontally();
        nameComponent.centerHorizontally();

        super.startRenderable();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        graphics.fill(10, 16, 98 + 10, 79+ 16, 0xff000000);
    }
}

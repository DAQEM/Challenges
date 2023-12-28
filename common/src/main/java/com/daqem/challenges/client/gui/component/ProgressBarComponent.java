package com.daqem.challenges.client.gui.component;

import com.daqem.uilib.api.client.gui.texture.ITexture;
import com.daqem.uilib.client.gui.component.*;
import com.daqem.uilib.client.gui.text.Text;
import com.daqem.uilib.client.gui.texture.Textures;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class ProgressBarComponent extends TextureComponent {

    private final Font font;
    private final int barColor;
    private final int value;
    private final int maxValue;

    private NineSlicedTextureComponent backgroundComponent;
    private SolidColorComponent progressBarComponent;
    private TextComponent progressTextComponent;

    public ProgressBarComponent(int x, int y, int width, int height, Font font, int barColor, int value, int maxValue) {
        super(null, x, y, width, height);
        this.font = font;
        this.barColor = barColor;
        this.value = value;
        this.maxValue = maxValue;
    }

    @Override
    public void startRenderable() {
        int progressWidth = (int) ((float) value / (float) maxValue * getWidth());
        String progressText = value + "/" + maxValue;

        backgroundComponent = new NineSlicedTextureComponent(Textures.SCROLL_BAR_BACKGROUND, 0, 0, getWidth(), getHeight());
        progressBarComponent = new SolidColorComponent(1, 1, progressWidth, getHeight() - 2, barColor);
        progressTextComponent = new TextComponent((getWidth() - font.width(progressText)) / 2, 1 + (getHeight() - font.lineHeight) / 2, new Text(font, Component.literal(progressText)));

        backgroundComponent.getColorManipulator().color(1.2F, 1.1F, 1F, 1F);

        addChildren(backgroundComponent, progressBarComponent, progressTextComponent);

        super.startRenderable();
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {

    }
}

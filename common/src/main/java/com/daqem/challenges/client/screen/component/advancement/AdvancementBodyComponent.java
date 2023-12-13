package com.daqem.challenges.client.screen.component.advancement;

import com.daqem.uilib.api.client.gui.text.IText;
import com.daqem.uilib.client.gui.component.NineSlicedTextureComponent;
import com.daqem.uilib.client.gui.texture.NineSlicedTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AdvancementBodyComponent extends NineSlicedTextureComponent {

    private final static ResourceLocation ADVANCEMENT_TEXTURE = new ResourceLocation("textures/gui/advancements/widgets.png");

    public AdvancementBodyComponent(int x, int y, int width, int height, IText<?> text) {
        super(new NineSlicedTexture(
                ADVANCEMENT_TEXTURE,
                0,
                0,
                256,
                256,
                6
        ), x, y, width, height, null, null, null);
        setText(text);
    }
}

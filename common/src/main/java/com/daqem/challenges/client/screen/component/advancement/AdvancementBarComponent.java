package com.daqem.challenges.client.screen.component.advancement;

import com.daqem.uilib.client.gui.component.NineSlicedTextureComponent;
import com.daqem.uilib.client.gui.texture.NineSlicedTexture;
import net.minecraft.resources.ResourceLocation;

public class AdvancementBarComponent extends NineSlicedTextureComponent {

    private final static ResourceLocation ADVANCEMENT_TEXTURE = new ResourceLocation("textures/gui/advancements/widgets.png");

    public AdvancementBarComponent(int x, int y, int width, boolean golden) {
        super(
                new NineSlicedTexture(
                        ADVANCEMENT_TEXTURE,
                        0,
                        golden ? 0 : 26,
                        200,
                        26,
                        6
                ), x, y, width, 26, null, null, null);
    }
}

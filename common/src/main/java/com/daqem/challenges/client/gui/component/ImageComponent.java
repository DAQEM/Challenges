package com.daqem.challenges.client.gui.component;

import com.daqem.uilib.api.client.gui.texture.ITexture;
import com.daqem.uilib.client.gui.component.TextureComponent;
import com.daqem.uilib.client.gui.texture.Texture;
import net.minecraft.resources.ResourceLocation;

public class ImageComponent extends TextureComponent {

    public ImageComponent(ResourceLocation resourceLocation, int x, int y) {
        super(new Texture(resourceLocation, (256 - 176) / 2, (256 - 120) / 2, 192, 160), x, y, 88, 60);
    }

    public ImageComponent(ITexture texture, int x, int y) {
        super(texture, x, y, 100, 100);
    }

    public ImageComponent(ITexture texture, int x, int y, int width, int height) {
        super(texture, x, y, width, height);
    }
}

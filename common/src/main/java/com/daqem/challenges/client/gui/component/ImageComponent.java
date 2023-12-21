package com.daqem.challenges.client.gui.component;

import com.daqem.uilib.api.client.gui.texture.ITexture;
import com.daqem.uilib.client.gui.component.TextureComponent;
import com.daqem.uilib.client.gui.texture.Texture;
import net.minecraft.resources.ResourceLocation;

public class ImageComponent extends TextureComponent {

    public ImageComponent(ResourceLocation resourceLocation, int x, int y) {
        super(new Texture(resourceLocation, 0, 0, 100, 100, 100, 100), x, y, 96, 77);
    }

    public ImageComponent(ITexture texture, int x, int y) {
        super(texture, x, y, 100, 100);
    }

    public ImageComponent(ITexture texture, int x, int y, int width, int height) {
        super(texture, x, y, width, height);
    }
}

package com.daqem.challenges.client.screen.component;

import com.daqem.uilib.client.gui.component.NineSlicedTextureComponent;
import com.daqem.uilib.client.gui.component.TextureComponent;
import com.daqem.uilib.client.gui.texture.Texture;
import com.daqem.uilib.client.gui.texture.Textures;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ChallengeComponent extends NineSlicedTextureComponent {

    private final TextureComponent itemBackgroundComponent;
    {
        Texture texture = new Texture(new ResourceLocation("textures/gui/advancements/widgets.png"), 26, 128, 26, 26);
        itemBackgroundComponent = new TextureComponent(texture, 0, 0, 26, 26);
    }

    private final ItemComponent itemComponent = new ItemComponent(5, 5, 16, 16, new ItemStack(Items.GLOW_LICHEN), false);

    public ChallengeComponent(int x, int y) {
        super(Textures.BUTTON, x, y, 100, 200, null, null,null);
        addChild(itemBackgroundComponent);
        addChild(itemComponent);
    }
}

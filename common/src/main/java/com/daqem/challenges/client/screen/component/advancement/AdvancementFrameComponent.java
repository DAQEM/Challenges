package com.daqem.challenges.client.screen.component.advancement;

import com.daqem.challenges.client.screen.component.ItemComponent;
import com.daqem.uilib.client.gui.component.TextureComponent;
import com.daqem.uilib.client.gui.texture.Texture;
import net.minecraft.advancements.FrameType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class AdvancementFrameComponent extends TextureComponent {

    private final static ResourceLocation ADVANCEMENT_TEXTURE = new ResourceLocation("textures/gui/advancements/widgets.png");
    private final static int FRAME_SIZE = 26;

    private final ItemComponent itemComponent;

    public AdvancementFrameComponent(int x, int y, FrameType frameType, ItemStack itemStack) {
        this(x, y, frameType, false, itemStack);
    }

    public AdvancementFrameComponent(int x, int y, FrameType frameType, boolean golden, ItemStack itemStack) {
        this(x, y, frameType, golden, itemStack, false);
    }

    public AdvancementFrameComponent(int x, int y, FrameType frameType, boolean golden, ItemStack itemStack, boolean decorated) {
        super(
                new Texture(
                        ADVANCEMENT_TEXTURE,
                        frameType.getTexture(),
                        golden ? 128 : 128 + FRAME_SIZE,
                        FRAME_SIZE,
                        FRAME_SIZE
                )
                , x, y, FRAME_SIZE, FRAME_SIZE);
        itemComponent = new ItemComponent(5, 5, 16, 16, itemStack, decorated);
    }

    @Override
    public void startRenderable() {
        super.startRenderable();
        addChild(itemComponent);
    }
}

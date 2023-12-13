package com.daqem.challenges.client.screen.component;

import com.daqem.uilib.api.client.gui.texture.ITexture;
import com.daqem.uilib.client.gui.component.AbstractComponent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class ItemComponent extends AbstractComponent<ItemComponent> {

    private final ItemStack itemStack;
    private final boolean decorated;

    public ItemComponent(int x, int y, int width, int height, ItemStack itemStack,boolean decorated) {
        super(null, x, y, width, height, null, null, null);
        this.itemStack = itemStack;
        this.decorated = decorated;
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        graphics.renderFakeItem(itemStack, 0, 0);
        if (decorated) {
            graphics.renderItemDecorations(Minecraft.getInstance().font, itemStack, 0, 0);
        }
    }
}

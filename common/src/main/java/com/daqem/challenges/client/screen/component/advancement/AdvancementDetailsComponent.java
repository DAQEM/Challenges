package com.daqem.challenges.client.screen.component.advancement;

import com.daqem.uilib.client.gui.component.AbstractComponent;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class AdvancementDetailsComponent extends AbstractComponent<AdvancementDetailsComponent> {

    private final AdvancementBarComponent advancementBarComponent;
    private final AdvancementFrameComponent advancementFrameComponent;


    public AdvancementDetailsComponent(int x, int y, int width, int height, FrameType frameType, boolean golden, ItemStack itemStack, boolean decorated) {
        super(null, x, y, width, height, null, null, null);
        this.advancementBarComponent = new AdvancementBarComponent(0, 0, width, golden);
        this.advancementFrameComponent = new AdvancementFrameComponent(3, 0, frameType, golden, itemStack, decorated);
    }

    @Override
    public void startRenderable() {
        super.startRenderable();
        addChild(advancementBarComponent);
        addChild(advancementFrameComponent);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {

    }
}

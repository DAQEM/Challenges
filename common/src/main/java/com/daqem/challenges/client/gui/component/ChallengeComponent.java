package com.daqem.challenges.client.gui.component;

import com.daqem.challenges.challenge.Challenge;
import com.daqem.uilib.client.gui.component.advancement.AdvancementHoverComponent;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.advancements.AdvancementWidgetType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ChallengeComponent extends AdvancementHoverComponent {

    public ChallengeComponent(Font font, Challenge challenge) {
        super(0, 0, 120, 180, font, new ItemStack(Items.STONE), challenge.getName(), challenge.getDescription(), AdvancementWidgetType.OBTAINED, FrameType.CHALLENGE);
    }
}

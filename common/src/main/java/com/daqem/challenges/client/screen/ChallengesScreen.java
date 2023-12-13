package com.daqem.challenges.client.screen;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.client.screen.component.ChallengeComponent;
import com.daqem.challenges.client.screen.component.advancement.AdvancementDetailsComponent;
import com.daqem.challenges.client.screen.component.text.MultiLineText;
import com.daqem.uilib.client.gui.AbstractScreen;
import com.daqem.uilib.client.gui.background.Backgrounds;
import com.daqem.uilib.client.gui.component.NineSlicedTextureComponent;
import com.daqem.uilib.client.gui.component.TextureComponent;
import com.daqem.uilib.client.gui.texture.Textures;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class ChallengesScreen extends AbstractScreen {

    ChallengeComponent challengeComponent = new ChallengeComponent(10, 10);

    MultiLineText text = new MultiLineText(
            font,
            Component.literal("Hello World!, Hello World!, Hello World!, Hello World!, Hello World!, Hello World!, Hello World!, Hello World!, Hello World!, Hello World!, Hello World!, Hello World!"),
            0,
            0,
            100,
            100
    );

    public ChallengesScreen() {
        super(Challenges.translatable("screen.challenges.title"));
    }

    @Override
    public void startScreen() {
        setBackground(Backgrounds.getDefaultBackground(width, height));
        addComponent(challengeComponent);

        addComponent(
                new AdvancementDetailsComponent(
                        150,
                        0,
                        200,
                        200,
                        FrameType.TASK,
                        false,
                        new ItemStack(Items.GLOW_LICHEN),
                        false
                )
        );


        NineSlicedTextureComponent component = new NineSlicedTextureComponent(
                Textures.BUTTON,
                0,
                0,
                100,
                100,
                text, null, null
        );
        addComponent(
                component
        );
    }

    @Override
    public void onTickScreen(GuiGraphics guiGraphics, int mouseX, int mouseY, float delta) {
        text.renderBase(guiGraphics, mouseX, mouseY, delta);
    }
}

package com.daqem.challenges.client.gui.component;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.ChallengeProgress;
import com.daqem.uilib.client.gui.component.TextComponent;
import com.daqem.uilib.client.gui.component.TextureComponent;
import com.daqem.uilib.client.gui.text.Text;
import com.daqem.uilib.client.gui.text.TruncatedText;
import com.daqem.uilib.client.gui.text.multiline.MultiLineText;
import com.daqem.uilib.client.gui.texture.Texture;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;

public class ChallengeBookComponent extends TextureComponent {

    private final Font font;
    private final ChallengeProgress challengeProgress;

    public ChallengeBookComponent(Font font, ChallengeProgress challengeProgress) {
        super(new Texture(Challenges.getId("textures/gui/book.png"), 0, 0, 287, 186, 287, 256), 0, 0, 287, 186);
        this.font = font;
        this.challengeProgress = challengeProgress;
    }

    @Override
    public void startRenderable() {


        TruncatedText nameText = new TruncatedText(font, Challenges.translatable("screen.book.description"), 0, 0, 96, font.lineHeight);
        nameText.setTextColor(0x222222);
        TextComponent nameComponent = new TextComponent(15, 14, nameText);
        nameComponent.setScale(1.25F);
        addChild(nameComponent);

        MultiLineText descriptionText = new MultiLineText(font, challengeProgress.getChallenge().getDescription(), 0, 0, 126);
        descriptionText.setTextColor(ChatFormatting.DARK_GRAY);
        TextComponent descriptionComponent = new TextComponent(15, 26, descriptionText);
        addChild(descriptionComponent);

        Text progressText = new Text(font, Challenges.translatable("screen.book.progress"), 0, 0);
        progressText.setTextColor(0x222222);
        TextComponent progressComponent = new TextComponent(15, 138, progressText);
        progressComponent.setScale(1.25F);
        addChild(progressComponent);

        ProgressBarComponent progressBarComponent = new ProgressBarComponent(15, 150, 120, font.lineHeight + 4, font, 0xFF55DD55, challengeProgress.getProgress(), challengeProgress.getChallenge().getGoal());
        addChild(progressBarComponent);



        TruncatedText rewardText = new TruncatedText(font, Challenges.translatable("screen.book.rewards"), 0, 0, 96, font.lineHeight);
        rewardText.setTextColor(0x222222);
        TextComponent rewardTextComponent = new TextComponent(150, 14, rewardText);
        rewardTextComponent.setScale(1.25F);
        addChild(rewardTextComponent);

        super.startRenderable();
    }
}

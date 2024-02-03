package com.daqem.challenges.client.gui.component;

import com.daqem.arc.Arc;
import com.daqem.arc.api.reward.IReward;
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

import java.util.List;

public class BookComponent extends TextureComponent {

    private final Font font;
    private final ChallengeProgress challengeProgress;

    public BookComponent(Font font, ChallengeProgress challengeProgress) {
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

        List<IReward> rewards = challengeProgress.getChallenge().getRewards();
        int offsetY = 26;
        float scale = .75F;
        for (IReward reward : rewards) {
            Text rewardName = new Text(font, reward.getName(), 0, 0);
            rewardName.setTextColor(0x222222);
            TextComponent rewardNameComponent = new TextComponent(150, offsetY, rewardName);
            addChild(rewardNameComponent);

            MultiLineText rewardDescription = new MultiLineText(font, reward.getDescription(), 0, 0, 126 / 3 * 4);
            rewardDescription.setTextColor(ChatFormatting.DARK_GRAY);
            TextComponent rewardDescriptionComponent = new TextComponent(150, offsetY + font.lineHeight, rewardDescription);
            rewardDescriptionComponent.setScale(scale);
            addChild(rewardDescriptionComponent);

            offsetY += (rewardDescription.getLines().size() + 1) * font.lineHeight + 4;
        }


        super.startRenderable();
    }
}

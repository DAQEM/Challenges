package com.daqem.challenges.integration.arc.reward;

import com.daqem.arc.api.action.data.ActionData;
import com.daqem.arc.api.action.result.ActionResult;
import com.daqem.arc.api.reward.AbstractReward;
import com.daqem.arc.api.reward.IReward;
import com.daqem.arc.api.reward.serializer.IRewardSerializer;
import com.daqem.arc.api.reward.serializer.RewardSerializer;
import com.daqem.arc.api.reward.type.IRewardType;
import com.daqem.challenges.player.ChallengesServerPlayer;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

public class ChallengeOccurrenceReward extends AbstractReward {

    private final int amount;

    public ChallengeOccurrenceReward(double chance, int priority, int amount) {
        super(chance, priority);
        this.amount = amount;
    }

    @Override
    public IRewardType<?> getType() {
        return ChallengesRewardType.CHALLENGE_OCCURRENCE;
    }

    @Override
    public IRewardSerializer<? extends IReward> getSerializer() {
        return ChallengesRewardSerializer.CHALLENGE_OCCURRENCE;
    }

    @Override
    public ActionResult apply(ActionData actionData) {
        if (actionData.getPlayer() instanceof ChallengesServerPlayer challengesServerPlayer) {
            challengesServerPlayer.challenges$getChallenge().ifPresent(challengeProgress -> challengeProgress.incrementProgress(challengesServerPlayer, amount));
        }
        return new ActionResult();
    }

    public static class Serializer implements RewardSerializer<ChallengeOccurrenceReward> {

        @Override
        public ChallengeOccurrenceReward fromJson(JsonObject jsonObject, double chance, int priority) {
            return new ChallengeOccurrenceReward(chance, priority, GsonHelper.getAsInt(jsonObject, "amount", 1));
        }

        @Override
        public ChallengeOccurrenceReward fromNetwork(FriendlyByteBuf friendlyByteBuf, double chance, int priority) {
            return new ChallengeOccurrenceReward(chance, priority, friendlyByteBuf.readInt());
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, ChallengeOccurrenceReward type) {
            RewardSerializer.super.toNetwork(friendlyByteBuf, type);
            friendlyByteBuf.writeInt(type.amount);
        }
    }
}

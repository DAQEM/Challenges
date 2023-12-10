package com.daqem.challenges.fabric.data;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.data.ChallengeManager;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;

public class ChallengeManagerFabric extends ChallengeManager implements IdentifiableResourceReloadListener {
    @Override
    public ResourceLocation getFabricId() {
        return Challenges.getId("challenges/challenges");
    }
}

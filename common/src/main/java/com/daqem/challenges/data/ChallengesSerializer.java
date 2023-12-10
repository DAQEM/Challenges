package com.daqem.challenges.data;

import com.daqem.arc.data.serializer.ArcSerializer;
import com.google.gson.JsonDeserializer;
import net.minecraft.network.FriendlyByteBuf;

public interface ChallengesSerializer<T> extends JsonDeserializer<T>, ArcSerializer {

    T fromNetwork(FriendlyByteBuf friendlyByteBuf);

    void toNetwork(FriendlyByteBuf friendlyByteBuf, T type);
}

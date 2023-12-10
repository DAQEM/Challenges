package com.daqem.challenges.challenge;

import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

public class ChallengeSerializerTests {

    private final Challenge.Serializer serializer = new Challenge.Serializer();

    @Test
    public void testChallengeSerializer() {
        JsonObject json = new JsonObject();
        json.addProperty("_location", "minecraft:stone");
        json.addProperty("goal", 1);
        Challenge challenge = serializer.deserialize(json, Challenge.class, null);
        assert challenge.getLocation().toString().equals("minecraft:stone");
        assert challenge.getGoal() == 1;
    }

}

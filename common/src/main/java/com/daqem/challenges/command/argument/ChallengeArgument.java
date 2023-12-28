package com.daqem.challenges.command.argument;

import com.daqem.challenges.Challenges;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.data.ChallengeManager;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class ChallengeArgument implements ArgumentType<Challenge> {

    private final ChallengeManager challengeManager;

    public ChallengeArgument() {
        this.challengeManager = Challenges.getPlatform().getChallengeManager();
    }

    public static ChallengeArgument challenge() {
        return new ChallengeArgument();
    }

    @Override
    public Challenge parse(StringReader reader) throws CommandSyntaxException {
        return challengeManager.getChallenge(ResourceLocation.read(reader)).orElseThrow(() -> {
            reader.setCursor(reader.getRemainingLength());
            return new CommandSyntaxException(null, Component.literal("Unknown challenge location: " + reader.getString()), reader.getString(), reader.getCursor());
        });
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(challengeManager.getChallengeLocationStrings(), builder);
    }

    public static Challenge getChallenge(CommandContext<?> context, String name) {
        return context.getArgument(name, Challenge.class);
    }
}

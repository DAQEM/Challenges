package com.daqem.challenges.command;

import com.daqem.arc.api.player.ArcPlayer;
import com.daqem.challenges.challenge.Challenge;
import com.daqem.challenges.challenge.ChallengeProgress;
import com.daqem.challenges.command.argument.ChallengeArgument;
import com.daqem.challenges.player.ChallengesServerPlayer;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.Nullable;

public class ChallengesCommand {

    public static void registerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands
                .literal("challenges")
                .requires(commandSourceStack -> commandSourceStack.hasPermission(2))
                .then(Commands.literal("debug")
                        .then(Commands.argument("target_player", EntityArgument.player())
                                .executes(context -> debug(context.getSource(), EntityArgument.getPlayer(context, "target_player")))
                        )
                        .executes(context -> debug(context.getSource(), context.getSource().getPlayer()))
                )
                .then(Commands.literal("set")
                        .then(Commands.argument("target_player", EntityArgument.player())
                                .then(Commands.literal("challenge")
                                        .then(Commands.argument("challenge", ChallengeArgument.challenge())
                                                .then(Commands.argument("progress", IntegerArgumentType.integer(0, Integer.MAX_VALUE))
                                                        .executes(context -> setChallenge(
                                                                context.getSource(),
                                                                EntityArgument.getPlayer(context, "target_player"),
                                                                ChallengeArgument.getChallenge(context, "challenge"),
                                                                IntegerArgumentType.getInteger(context, "progress")
                                                                ))
                                                )
                                                .executes(context -> setChallenge(
                                                        context.getSource(),
                                                        EntityArgument.getPlayer(context, "target_player"),
                                                        ChallengeArgument.getChallenge(context, "challenge"),
                                                        0
                                                        )
                                                )
                                        )
                                        .then(Commands.literal("none")
                                                .executes(context -> setChallenge(
                                                        context.getSource(),
                                                        EntityArgument.getPlayer(context, "target_player"),
                                                        null,
                                                        0
                                                        )
                                                )
                                        )
                                )
                                .then(Commands.literal("progress")
                                        .then(Commands.argument("progress", IntegerArgumentType.integer(0, Integer.MAX_VALUE))
                                                .executes(context -> setProgress(
                                                        context.getSource(),
                                                        EntityArgument.getPlayer(context, "target_player"),
                                                        IntegerArgumentType.getInteger(context, "progress")
                                                        )
                                                )
                                        )
                                )
                        )
                )
        );
    }

    private static int debug(CommandSourceStack source, ServerPlayer target) {
        if (target instanceof ArcPlayer arcPlayer) {
            arcPlayer.arc$getActionHolders().forEach(actionHolder -> {
                source.sendSuccess(() -> Component.literal(actionHolder.getLocation().toString()), false);
                source.sendSuccess(() -> Component.literal("actions: " + actionHolder.getActions().size()), false);
                source.sendSuccess(() -> Component.literal(" "), false);
            });
        }
        return 0;
    }

    private static int setChallenge(CommandSourceStack source, ServerPlayer target, @Nullable Challenge challenge, int progress) {
        if (challenge != null && progress >= challenge.getGoal()) {
            source.sendFailure(Component.literal("Progress must be less than the goal: " + challenge.getGoal()));
        }
        if (target instanceof ChallengesServerPlayer serverPlayer) {
            if (challenge == null) {
                serverPlayer.challenges$removeChallenge();
            } else {
                serverPlayer.challenges$setChallenge(new ChallengeProgress(challenge, progress));
            }
        }
        return 0;
    }

    private static int setProgress(CommandSourceStack source, ServerPlayer target, int progress) {
        if (target instanceof ChallengesServerPlayer serverPlayer) {
            serverPlayer.challenges$getChallenge().ifPresentOrElse(
                    challengeProgress -> {
                        if (progress > challengeProgress.getChallenge().getGoal()) {
                            source.sendFailure(Component.literal("Progress cannot be greater than the goal: " + challengeProgress.getChallenge().getGoal()));
                        } else {
                            serverPlayer.challenges$setChallengeProgress(progress);
                            source.sendSuccess(() -> Component.literal("Set progress of " + target.getDisplayName().getString() + "'s challenge to " + progress), false);
                        }
                    },
                    () -> source.sendFailure(Component.literal("Player does not have a challenge")));
        }
        return 0;
    }
}

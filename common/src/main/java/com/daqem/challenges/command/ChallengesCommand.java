package com.daqem.challenges.command;

import com.daqem.arc.api.player.ArcPlayer;
import com.daqem.challenges.Challenges;
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

    private static int setChallenge(CommandSourceStack source, ServerPlayer target, @Nullable Challenge challenge, int progress) {
        if (challenge != null && progress >= challenge.getGoal()) {
            source.sendFailure(Challenges.prefixedTranslatable("command.set.challenge.failure.progress", challenge.getGoal()));
        } else {
            if (target instanceof ChallengesServerPlayer serverPlayer) {
                if (challenge != null) {
                    serverPlayer.challenges$setChallenge(new ChallengeProgress(challenge, progress));
                    if (progress == 0) {
                        if (source.getPlayer() == target) {
                            source.sendSuccess(() -> Challenges.prefixedTranslatable("command.set.challenge.success.self", challenge.getStyledName()), false);
                        } else {
                            source.sendSuccess(() -> Challenges.prefixedTranslatable("command.set.challenge.success.other", target.getDisplayName().getString(), challenge.getStyledName()), false);
                            target.sendSystemMessage(Challenges.prefixedTranslatable("command.set.challenge.success.other.player", challenge.getStyledName()), false);
                        }
                    } else {
                        if (source.getPlayer() == target) {
                            source.sendSuccess(() -> Challenges.prefixedTranslatable("command.set.challenge.success.self.progress", challenge.getStyledName(), progress), false);
                        } else {
                            source.sendSuccess(() -> Challenges.prefixedTranslatable("command.set.challenge.success.other.progress", target.getDisplayName().getString(), challenge.getStyledName(), progress), false);
                            target.sendSystemMessage(Challenges.prefixedTranslatable("command.set.challenge.success.other.player.progress", challenge.getStyledName(), progress), false);
                        }
                    }
                } else {
                    serverPlayer.challenges$removeChallenge();
                    if (source.getPlayer() == target) {
                        source.sendSuccess(() -> Challenges.prefixedTranslatable("command.set.challenge.success.self.none"), false);
                    } else {
                        source.sendSuccess(() -> Challenges.prefixedTranslatable("command.set.challenge.success.other.none", target.getDisplayName().getString()), false);
                        target.sendSystemMessage(Challenges.prefixedTranslatable("command.set.challenge.success.other.player.none"), false);
                    }
                }
            }
        }
        return 0;
    }

    private static int setProgress(CommandSourceStack source, ServerPlayer target, int progress) {
        if (target instanceof ChallengesServerPlayer serverPlayer) {
            serverPlayer.challenges$getChallenge().ifPresentOrElse(
                    challengeProgress -> {
                        if (progress > challengeProgress.getChallenge().getGoal()) {
                            source.sendFailure(Challenges.prefixedTranslatable("command.set.progress.failure.progress", challengeProgress.getChallenge().getGoal()));
                        } else {
                            serverPlayer.challenges$setChallengeProgress(progress);
                            if (source.getPlayer() == target) {
                                source.sendSuccess(() -> Challenges.prefixedTranslatable("command.set.progress.success.self", progress), false);
                            } else {
                                source.sendSuccess(() -> Challenges.prefixedTranslatable("command.set.progress.success.other", target.getDisplayName().getString(), progress), false);
                                target.sendSystemMessage(Challenges.prefixedTranslatable("command.set.progress.success.other.player", progress), false);
                            }
                        }
                    },
                    () -> {
                        if (source.getPlayer() == target) {
                            source.sendFailure(Challenges.prefixedTranslatable("command.set.progress.failure.none.self"));
                        } else {
                            source.sendFailure(Challenges.prefixedTranslatable("command.set.progress.failure.none.other", target.getDisplayName().getString()));
                        }
                    });
        }
        return 0;
    }
}

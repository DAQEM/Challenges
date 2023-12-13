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
                .then(Commands.literal("add")
                        .then(Commands.argument("target_player", EntityArgument.player())
                                .then(Commands.literal("challenge")
                                        .then(Commands.argument("challenge", ChallengeArgument.challenge())
                                                .then(Commands.argument("progress", IntegerArgumentType.integer())
                                                        .executes(context -> addChallenge(
                                                                context.getSource(),
                                                                EntityArgument.getPlayer(context, "target_player"),
                                                                ChallengeArgument.getChallenge(context, "challenge"),
                                                                IntegerArgumentType.getInteger(context, "progress")
                                                                ))
                                                )
                                                .executes(context -> addChallenge(
                                                        context.getSource(),
                                                        EntityArgument.getPlayer(context, "target_player"),
                                                        ChallengeArgument.getChallenge(context, "challenge"),
                                                        0
                                                        ))
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

    private static int addChallenge(CommandSourceStack source, ServerPlayer target, Challenge challenge, int progress) {
        if (progress >= challenge.getGoal()) {
            source.sendFailure(Component.literal("Progress must be less than the goal: " + challenge.getGoal()));
        }
        if (target instanceof ChallengesServerPlayer serverPlayer) {
            serverPlayer.challenges$addChallenge(challenge, progress);
        }
        return 0;
    }
}

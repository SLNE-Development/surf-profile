package dev.slne.surf.profile.paper.command

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.literalArgument
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.api.core.command.args.awaiting
import dev.slne.surf.api.paper.command.executors.playerExecutorSuspend
import dev.slne.surf.api.paper.inventory.framework.open
import dev.slne.surf.api.paper.inventory.framework.viewFrame
import dev.slne.surf.core.api.common.player.SurfPlayer
import dev.slne.surf.core.api.paper.command.argument.surfOfflinePlayerArgument
import dev.slne.surf.profile.paper.menu.ProfileView
import dev.slne.surf.profile.paper.menu.own.ownProfileMenu

fun profileCommand() = commandTree("profile") {
    withPermission("surf.profile.command.profile")
    playerExecutor { player, _ ->
        ownProfileMenu().open(player)
    }

    literalArgument("new") {
        surfOfflinePlayerArgument("target") {
            playerExecutorSuspend { player, arguments ->
                val target = arguments.awaiting<SurfPlayer>("target")

                viewFrame.open(ProfileView::class.java, player, mapOf("target" to target))
            }
        }
    }
}
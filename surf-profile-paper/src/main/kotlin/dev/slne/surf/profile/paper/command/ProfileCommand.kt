package dev.slne.surf.profile.paper.command

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.api.core.command.args.awaiting
import dev.slne.surf.api.paper.command.executors.playerExecutorSuspend
import dev.slne.surf.api.paper.inventory.framework.viewFrame
import dev.slne.surf.core.api.common.player.SurfPlayer
import dev.slne.surf.core.api.paper.command.argument.surfOfflinePlayerArgument
import dev.slne.surf.core.api.paper.util.surfPlayer
import dev.slne.surf.profile.paper.menu.ProfileView

fun profileCommand() = commandTree("profile") {
    withPermission("surf.profile.command.profile")
    playerExecutor { player, _ ->
        viewFrame.open(ProfileView::class.java, player, mapOf("target" to player.surfPlayer))
    }

    surfOfflinePlayerArgument("target") {
        withPermission("surf.profile.command.profile.other")
        playerExecutorSuspend { player, arguments ->
            val target = arguments.awaiting<SurfPlayer>("target")

            viewFrame.open(ProfileView::class.java, player, mapOf("target" to target))
        }
    }
}
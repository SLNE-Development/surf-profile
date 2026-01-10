package dev.slne.surf.profile.paper.command

import dev.jorel.commandapi.kotlindsl.commandTree
import dev.jorel.commandapi.kotlindsl.playerExecutor
import dev.slne.surf.profile.paper.menu.own.ownProfileMenu

fun profileCommand() = commandTree("profile") {
    withPermission("surf.profile.command.profile")
    playerExecutor { player, _ ->
        ownProfileMenu(player)
    }
}
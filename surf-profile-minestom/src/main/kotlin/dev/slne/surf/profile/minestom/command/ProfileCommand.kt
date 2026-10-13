package dev.slne.surf.profile.minestom.command

import dev.slne.surf.api.minestom.command.dsl.commandTree
import dev.slne.surf.api.minestom.command.dsl.playerExecutor
import dev.slne.surf.api.minestom.inventory.framework.open
import dev.slne.surf.profile.core.client.permission.ProfilePermissions
import dev.slne.surf.profile.minestom.menu.own.ownProfileMenu

fun profileCommand() = commandTree("profile") {
    withPermission(ProfilePermissions.COMMAND_PROFILE)
    playerExecutor { player, _ ->
        ownProfileMenu.open(player)
    }
}

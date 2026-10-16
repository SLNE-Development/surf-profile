package dev.slne.surf.profile.minestom

import dev.slne.surf.api.minestom.inventory.framework.register
import dev.slne.surf.api.minestom.plugin.SurfMinestomPlugin
import dev.slne.surf.profile.minestom.command.profileCommand
import dev.slne.surf.profile.minestom.menu.own.ownProfileMenu

object SurfProfileMinestomPlugin : SurfMinestomPlugin() {
    override suspend fun onLoad() {
        ownProfileMenu.register()
    }

    override suspend fun onEnable() {
        profileCommand()
    }
}

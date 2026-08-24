package dev.slne.surf.profile.minestom

import com.google.auto.service.AutoService
import dev.slne.minestom.lobby.api.extension.ConnectionManager
import dev.slne.minestom.lobby.api.player.getOnlineLobbyPlayerByUuid
import dev.slne.surf.api.minestom.inventory.framework.open
import dev.slne.surf.profile.core.client.platform.ProfilePlatform
import dev.slne.surf.profile.minestom.menu.own.ownProfileMenu
import java.util.*

@AutoService(ProfilePlatform::class)
class MinestomProfilePlatform : ProfilePlatform {
    override fun openOwnProfileMenu(playerUuid: UUID) {
        val player = ConnectionManager.getOnlineLobbyPlayerByUuid(playerUuid) ?: return
        player.scheduleNextTick { ownProfileMenu.open(player) }
    }
}

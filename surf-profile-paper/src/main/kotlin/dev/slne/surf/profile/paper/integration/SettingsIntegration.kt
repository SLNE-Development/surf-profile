package dev.slne.surf.profile.paper.integration

import dev.slne.surf.settings.api.SurfSettingsApi
import org.bukkit.entity.Player

object SettingsIntegration {
    fun openMenu(player: Player) {
        SurfSettingsApi.openSettingsGui(player.uniqueId)
    }
}
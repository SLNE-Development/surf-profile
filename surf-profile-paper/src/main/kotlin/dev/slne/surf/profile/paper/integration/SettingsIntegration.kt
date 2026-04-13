package dev.slne.surf.profile.paper.integration

import dev.slne.surf.settings.api.SurfSettingsApi
import org.bukkit.entity.HumanEntity

object SettingsIntegration {
    fun openMenu(player: HumanEntity) {
        SurfSettingsApi.openSettingsGui(player.uniqueId)
    }
}
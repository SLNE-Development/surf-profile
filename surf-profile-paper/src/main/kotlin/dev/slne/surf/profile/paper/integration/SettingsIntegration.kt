package dev.slne.surf.profile.paper.integration

import dev.slne.surf.settings.api.surfSettingsApi
import dev.slne.surf.surfapi.core.api.messages.adventure.sendText
import org.bukkit.Bukkit
import org.bukkit.entity.HumanEntity

object SettingsIntegration {
    fun isEnabled() = Bukkit.getPluginManager().isPluginEnabled("surf-settings-paper")

    fun openMenu(player: HumanEntity) {
        if (isEnabled()) {
            surfSettingsApi.openSettingsGui(player.uniqueId)
        } else {
            player.sendText {
                appendErrorPrefix()
                error("Profile: Internal Server error while handling settings hook. Is everything loaded correctly?")
            }
        }
    }
}
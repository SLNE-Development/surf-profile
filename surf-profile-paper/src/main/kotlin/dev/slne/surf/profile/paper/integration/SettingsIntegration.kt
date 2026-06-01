package dev.slne.surf.profile.paper.integration

import dev.slne.surf.api.paper.util.namespacedKey
import dev.slne.surf.settings.api.SurfSettingsApi
import dev.slne.surf.settings.api.setting.SettingKey
import org.bukkit.entity.Player
import java.util.*

object SettingsIntegration {
    val twitchPrivacySettingKey = SettingKey.ofBoolean(namespacedKey("twitch_privacy"), false)
    val discordPrivacySettingKey = SettingKey.ofBoolean(namespacedKey("discord_privacy"), false)

    suspend fun createSettings() {
        SurfSettingsApi.createSetting(twitchPrivacySettingKey)
        SurfSettingsApi.createSetting(discordPrivacySettingKey)
    }

    suspend fun hasTwitchEnabled(playerUuid: UUID) =
        SurfSettingsApi.getSettingValue(playerUuid, twitchPrivacySettingKey)

    suspend fun hasDiscordEnabled(playerUuid: UUID) =
        SurfSettingsApi.getSettingValue(playerUuid, discordPrivacySettingKey)

    fun openMenu(player: Player) {
        SurfSettingsApi.openSettingsGui(player.uniqueId)
    }
}
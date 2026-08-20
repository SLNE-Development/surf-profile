package dev.slne.surf.profile.core.client.integration

import dev.slne.surf.settings.api.SurfSettingsApi
import java.util.*

object SettingsIntegration {
    fun openMenu(playerUuid: UUID) {
        SurfSettingsApi.openSettingsGui(playerUuid)
    }
}

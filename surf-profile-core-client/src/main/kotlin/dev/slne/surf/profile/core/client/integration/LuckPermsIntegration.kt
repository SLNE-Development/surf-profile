package dev.slne.surf.profile.core.client.integration

import dev.slne.surf.api.core.luckperms.LuckPermsAccess
import dev.slne.surf.api.core.minimessage.miniMessage
import net.kyori.adventure.text.Component
import java.util.*

object LuckPermsIntegration {
    fun getRang(playerUuid: UUID): Component = miniMessage.deserialize(
        LuckPermsAccess.getUser(playerUuid)?.cachedData?.metaData?.prefix ?: "/"
    )
}

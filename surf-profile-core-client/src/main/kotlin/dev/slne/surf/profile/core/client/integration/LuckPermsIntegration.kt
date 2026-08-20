package dev.slne.surf.profile.core.client.integration

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.luckperms.api.LuckPermsProvider
import java.util.*

object LuckPermsIntegration {
    private val luckPerms by lazy {
        LuckPermsProvider.get()
    }

    fun getRang(playerUuid: UUID): Component = MiniMessage.miniMessage().deserialize(
        luckPerms.userManager.getUser(playerUuid)?.cachedData?.metaData?.prefix ?: "/"
    )
}
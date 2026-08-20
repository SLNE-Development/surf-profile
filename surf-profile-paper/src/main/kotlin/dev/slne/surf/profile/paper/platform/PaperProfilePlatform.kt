package dev.slne.surf.profile.paper.platform

import com.google.auto.service.AutoService
import dev.slne.surf.api.paper.inventory.framework.open
import dev.slne.surf.profile.core.client.platform.ProfilePlatform
import dev.slne.surf.profile.paper.menu.own.ownProfileMenu
import net.kyori.adventure.util.Services
import org.bukkit.Bukkit
import java.util.*

@AutoService(ProfilePlatform::class)
class PaperProfilePlatform : ProfilePlatform, Services.Fallback {
    override fun openOwnProfileMenu(playerUuid: UUID) {
        Bukkit.getPlayer(playerUuid)?.let {
            ownProfileMenu.open(it)
        }
    }
}

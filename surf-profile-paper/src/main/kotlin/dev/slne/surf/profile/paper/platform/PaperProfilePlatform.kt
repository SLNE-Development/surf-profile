package dev.slne.surf.profile.paper.platform

import com.github.shynixn.mccoroutine.folia.entityDispatcher
import com.github.shynixn.mccoroutine.folia.launch
import com.google.auto.service.AutoService
import dev.slne.surf.api.paper.inventory.framework.open
import dev.slne.surf.profile.core.client.platform.ProfilePlatform
import dev.slne.surf.profile.paper.menu.own.ownProfileMenu
import dev.slne.surf.profile.paper.plugin
import net.kyori.adventure.util.Services
import org.bukkit.Bukkit
import java.util.*

@AutoService(ProfilePlatform::class)
class PaperProfilePlatform : ProfilePlatform, Services.Fallback {
    override fun openOwnProfileMenu(playerUuid: UUID) {
        val player = Bukkit.getPlayer(playerUuid) ?: return
        plugin.launch(plugin.entityDispatcher(player)) {
            ownProfileMenu.open(player)
        }
    }
}

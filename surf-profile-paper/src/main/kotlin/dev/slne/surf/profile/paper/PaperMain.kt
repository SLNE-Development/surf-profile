package dev.slne.surf.profile.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.api.paper.extensions.pluginManager
import dev.slne.surf.api.paper.inventory.framework.register
import dev.slne.surf.profile.paper.command.profileCommand
import dev.slne.surf.profile.paper.menu.ProfileView
import dev.slne.surf.profile.paper.menu.own.ownProfileMenu
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override fun onLoad() {
        ownProfileMenu().register()

        ProfileView.register()
    }

    override fun onEnable() {
        profileCommand()
    }


    fun hasClanHook() = pluginManager.isPluginEnabled("surf-clan-paper")
    fun hasFriendsHook() = pluginManager.isPluginEnabled("surf-friends-paper")
    fun hasSocialsHook() = pluginManager.isPluginEnabled("surf-social-paper")
    fun hasTrophiesHook() = pluginManager.isPluginEnabled("surf-trophy-paper")
}
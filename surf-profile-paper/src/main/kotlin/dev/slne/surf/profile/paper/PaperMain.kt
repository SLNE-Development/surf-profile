package dev.slne.surf.profile.paper

import com.github.shynixn.mccoroutine.folia.SuspendingJavaPlugin
import dev.slne.surf.api.paper.inventory.framework.register
import dev.slne.surf.profile.paper.command.profileCommand
import dev.slne.surf.profile.paper.menu.own.ownProfileMenu
import org.bukkit.plugin.java.JavaPlugin

val plugin get() = JavaPlugin.getPlugin(PaperMain::class.java)

class PaperMain : SuspendingJavaPlugin() {
    override fun onLoad() {
        ownProfileMenu.register()
    }

    override fun onEnable() {
        profileCommand()
    }
}
package dev.slne.surf.profile.minestom

import com.google.auto.service.AutoService
import dev.slne.minestom.lobby.api.plugin.MinestomPlugin
import dev.slne.minestom.lobby.api.plugin.annotation.MinestomPluginMeta
import dev.slne.surf.profile.minestom.command.ProfileCommandRegistrar

@AutoService(MinestomPlugin::class)
@MinestomPluginMeta(
    "surf-profile-minestom",
    dependsOn = [
        "surf-api-minestom",
        "surf-settings-minestom",
        "surf-playtime-minestom"
    ]
)
class SurfProfileMinestomPlugin :
    MinestomPlugin(SurfProfileMinestomEntrypoint::class.java) {
    override fun configurePlugin() {
        bindCommandRegistrar<ProfileCommandRegistrar>()
    }
}

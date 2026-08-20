package dev.slne.surf.profile.minestom

import com.google.inject.Inject
import com.google.inject.Singleton
import dev.slne.minestom.lobby.api.plugin.MinestomPluginEntrypoint
import dev.slne.minestom.lobby.api.plugin.annotation.DataDirectory
import dev.slne.surf.api.minestom.inventory.framework.register
import dev.slne.surf.profile.minestom.menu.own.ownProfileMenu
import java.nio.file.Path

@Singleton
class SurfProfileMinestomEntrypoint @Inject constructor(
    @DataDirectory path: Path
) : MinestomPluginEntrypoint {
    init {
        dataPath = path
    }

    override suspend fun start() {
        ownProfileMenu.register()
    }

    companion object {
        lateinit var dataPath: Path
            private set
    }
}

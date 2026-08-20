package dev.slne.surf.profile.minestom.command

import com.google.inject.Singleton
import dev.slne.minestom.lobby.api.command.CommandRegistrar

@Singleton
class ProfileCommandRegistrar : CommandRegistrar {
    override fun register() {
        profileCommand()
    }
}

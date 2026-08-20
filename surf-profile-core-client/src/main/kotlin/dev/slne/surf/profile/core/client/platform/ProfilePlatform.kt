package dev.slne.surf.profile.core.client.platform

import dev.slne.surf.api.core.util.requiredService
import java.util.*

private val platform = requiredService<ProfilePlatform>()

/**
 * The platform-specific operations the shared profile code depends on.
 *
 * One implementation is contributed per platform through `ServiceLoader`.
 */
interface ProfilePlatform {

    /**
     * Opens the own-profile menu for the player identified by [playerUuid], if they are online.
     */
    fun openOwnProfileMenu(playerUuid: UUID)

    companion object : ProfilePlatform by platform
}

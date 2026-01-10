package dev.slne.surf.profile.api

import dev.slne.surf.surfapi.core.api.util.requiredService
import java.util.*

val surfProfileApi = requiredService<SurfProfileApi>()

interface SurfProfileApi {
    fun openOwnProfileMenu(playerUuid: UUID)
}
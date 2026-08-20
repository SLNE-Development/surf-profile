package dev.slne.surf.profile.core.client.api

import com.google.auto.service.AutoService
import dev.slne.surf.profile.api.SurfProfileApi
import dev.slne.surf.profile.core.client.platform.ProfilePlatform
import net.kyori.adventure.util.Services
import java.util.*

@AutoService(SurfProfileApi::class)
class SurfProfileApiImpl : SurfProfileApi, Services.Fallback {
    override fun openOwnProfileMenu(playerUuid: UUID) {
        ProfilePlatform.openOwnProfileMenu(playerUuid)
    }
}

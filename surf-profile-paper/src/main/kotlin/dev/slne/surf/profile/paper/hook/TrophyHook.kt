package dev.slne.surf.profile.paper.hook

import dev.slne.surf.profile.paper.mapped.MappedTrophy
import dev.slne.surf.trophy.api.surfTrophyApi
import java.time.OffsetDateTime
import java.util.*

object TrophyHook {
    suspend fun getTrophies(playerUuid: UUID) = listOf(
        MappedTrophy(
            name = "Testtrophäe",
            receivedAt = OffsetDateTime.now(),
        )
    )

    fun openTrophyMenu(playerUuid: UUID, viewer: UUID) =
        surfTrophyApi.showTrophyMenu(playerUuid, viewer)
}
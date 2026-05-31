package dev.slne.surf.profile.paper.hook

import dev.slne.clan.api.clan.Clan
import dev.slne.surf.profile.paper.mapped.MappedClan
import java.util.*

object ClanHook {
    suspend fun getMappedClan(playerUuid: UUID) = Clan.byPlayer(playerUuid)?.let {
        MappedClan(
            clanName = it.name,
            clanTag = it.tag,
            playersClanRole = it.getMember(playerUuid)?.role?.name ?: "Unbekannt",
            clanMemberCount = it.members.size
        )
    } ?: MappedClan.empty()
}
package dev.slne.surf.profile.paper.hook

import dev.slne.surf.social.api.SurfSocialApi
import dev.slne.surf.social.api.connection.impl.DiscordConnection
import dev.slne.surf.social.api.connection.impl.TwitchConnection
import dev.slne.surf.social.api.findConnection
import java.util.*

object SocialsHook {
    suspend fun getTwitchName(playerUuid: UUID) =
        SurfSocialApi.findConnection<TwitchConnection>(playerUuid)?.twitchName

    suspend fun getDiscordName(playerUuid: UUID) =
        SurfSocialApi.findConnection<DiscordConnection>(playerUuid)?.discordName
}
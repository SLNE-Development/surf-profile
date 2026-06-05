package dev.slne.surf.profile.paper.hook

import dev.slne.surf.profile.paper.integration.SettingsIntegration
import dev.slne.surf.social.api.SurfSocialApi
import dev.slne.surf.social.api.connection.impl.DiscordConnection
import dev.slne.surf.social.api.connection.impl.TwitchConnection
import dev.slne.surf.social.api.findConnection
import java.util.*

object SocialsHook {
    suspend fun getTwitchName(playerUuid: UUID) =
        if (SettingsIntegration.hasTwitchEnabled(playerUuid)) SurfSocialApi.findConnection<TwitchConnection>(
            playerUuid
        )?.twitchName else "/"

    suspend fun getDiscordName(playerUuid: UUID) =
        if (SettingsIntegration.hasDiscordEnabled(playerUuid)) SurfSocialApi.findConnection<DiscordConnection>(
            playerUuid
        )?.discordName else "/"
}
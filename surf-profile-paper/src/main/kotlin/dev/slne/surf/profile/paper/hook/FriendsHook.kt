package dev.slne.surf.profile.paper.hook

import dev.slne.surf.friends.api.player.FriendsPlayer
import dev.slne.surf.profile.paper.mapped.MappedFriends
import java.util.*

object FriendsHook {
    fun getMappedFriends(playerUuid: UUID) =
        FriendsPlayer[playerUuid].friendships.size.let {
            MappedFriends(
                friendCount = it
            )
        }

}
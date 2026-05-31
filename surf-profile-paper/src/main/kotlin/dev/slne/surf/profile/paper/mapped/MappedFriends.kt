package dev.slne.surf.profile.paper.mapped

data class MappedFriends(
    val friendCount: Int,
    val loading: Boolean = false,
) {
    companion object {
        fun loading() = MappedFriends(
            friendCount = 0,
            loading = true
        )
    }
}

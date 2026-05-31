package dev.slne.surf.profile.paper.mapped

data class MappedClan(
    val clanName: String,
    val clanTag: String,
    val playersClanRole: String,
    val clanMemberCount: Int,

    val empty: Boolean = false,
    val loading: Boolean = false,
) {
    companion object {
        fun empty() = MappedClan(
            clanName = "",
            clanTag = "",
            playersClanRole = "",
            clanMemberCount = 0,
            empty = true,
            loading = false
        )

        fun loading() = MappedClan(
            clanName = "",
            clanTag = "",
            playersClanRole = "",
            clanMemberCount = 0,
            empty = false,
            loading = true
        )
    }
}

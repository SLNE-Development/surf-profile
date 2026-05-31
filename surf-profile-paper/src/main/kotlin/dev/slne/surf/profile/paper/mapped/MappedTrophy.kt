package dev.slne.surf.profile.paper.mapped

import java.time.OffsetDateTime

data class MappedTrophy(
    val name: String,
    val receivedAt: OffsetDateTime,

    val loading: Boolean = false
) {
    companion object {
        fun loading() = MappedTrophy(
            name = "",
            receivedAt = OffsetDateTime.MIN,
            loading = true
        )
    }
}

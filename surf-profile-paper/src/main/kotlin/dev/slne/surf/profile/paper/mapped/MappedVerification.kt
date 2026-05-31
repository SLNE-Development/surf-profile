package dev.slne.surf.profile.paper.mapped

import net.kyori.adventure.text.Component

data class MappedVerification(
    val verified: Boolean,
    val verificationText: String,

    val rank: Component,
    val loading: Boolean = false,
) {
    companion object {
        fun loading() = MappedVerification(
            verified = false,
            verificationText = "",
            rank = Component.empty(),
            loading = true
        )
    }
}

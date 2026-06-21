package dev.slne.surf.profile.paper.mapped

data class MappedVerification(
    val verified: Boolean,
    val verificationText: String,
    val loading: Boolean = false,
) {
    companion object {
        fun loading() = MappedVerification(
            verified = false,
            verificationText = "",
            loading = true
        )
    }
}

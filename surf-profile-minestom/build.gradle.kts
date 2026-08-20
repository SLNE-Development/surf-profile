plugins {
    id("dev.slne.surf.api.gradle.minestom")
}

surfMinestomApi {
    withCoreMinestom()
}

dependencies {
    api(projects.surfProfileCoreClient)

    compileOnly("dev.slne.surf.playtime:surf-playtime-api-common:+")
    compileOnly("dev.slne.surf.settings:surf-settings-api:+")
}

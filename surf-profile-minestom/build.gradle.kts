plugins {
    id("dev.slne.surf.api.gradle.minestom")
}

repositories {
    mavenLocal()
}

surfMinestomApi {
    withCoreMinestom()
}

minestomPluginFile {
    main = "dev.slne.surf.profile.minestom.SurfProfileMinestomPlugin"
    authors = listOf("red")

    pluginDependencies {
        register("surf-settings-minestom")
        register("surf-playtime-minestom")
    }
}

dependencies {
    api(projects.surfProfileCoreClient)

    compileOnly("dev.slne.surf.playtime:surf-playtime-api-common:+")
    compileOnly("dev.slne.surf.settings:surf-settings-api:+")
}

import dev.slne.surf.surfapi.gradle.util.registerSoft

plugins {
    id("dev.slne.surf.surfapi.gradle.paper-plugin")
}

surfPaperPluginApi {
    mainClass("dev.slne.surf.profile.paper.PaperMain")
    generateLibraryLoader(false)
    foliaSupported(true)

    withCorePaper()

    authors.add("red")

    serverDependencies {
        registerSoft("surf-playtime-paper")
        register("LuckPerms")
    }
}

dependencies {
    api(project(":surf-profile-core"))
    compileOnly("dev.slne.surf.playtime:surf-playtime-api:1.21.11-1.0.2-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")
}
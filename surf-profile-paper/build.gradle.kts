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
        registerSoft("surf-settings-paper")
        register("LuckPerms")
    }
}

dependencies {
    api(project(":surf-profile-core"))
    compileOnly("net.luckperms:api:5.4")
    compileOnly("dev.slne.surf.playtime:surf-playtime-api-paper:1.21.11-1.2.1-SNAPSHOT")
    compileOnly("dev.slne.surf.settings:surf-settings-api:1.21.11-2.0.1-SNAPSHOT")
}
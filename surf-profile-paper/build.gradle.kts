import dev.slne.surf.api.gradle.util.registerSoft

plugins {
    id("dev.slne.surf.api.gradle.paper-plugin")
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
    api(projects.surfProfileApi)
    compileOnly("net.luckperms:api:5.4")
    compileOnly("dev.slne.surf.playtime:surf-playtime-api-paper:+")
    compileOnly("dev.slne.surf.settings:surf-settings-api:+")
}
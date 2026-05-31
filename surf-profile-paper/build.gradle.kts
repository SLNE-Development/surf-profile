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
        registerSoft("surf-clan-paper")
        registerSoft("surf-friends-paper")
        registerSoft("surf-trophy-paper")
        register("surf-social-paper")
        register("LuckPerms")
    }
}

dependencies {
    api(projects.surfProfileApi)
    compileOnly("net.luckperms:api:5.4")
    compileOnly("dev.slne.surf.playtime:surf-playtime-api-paper:+")
    compileOnly("dev.slne.surf.settings:surf-settings-api:+")

    compileOnly("dev.slne.surf.clan:surf-clan-api:+")
    compileOnly("dev.slne.surf.friends:surf-friends-api:+")
    compileOnly("dev.slne.surf.trophy:surf-trophy-api:+")
    compileOnly("dev.slne.surf.social:surf-social-api:+")
}
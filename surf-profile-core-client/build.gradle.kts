plugins {
    id("dev.slne.surf.api.gradle.core")
}

surfCoreApi {
    withCoreCommon()
}

dependencies {
    api(projects.surfProfileApi)

    compileOnly("dev.slne.surf.playtime:surf-playtime-api-common:+")
    compileOnly("dev.slne.surf.settings:surf-settings-api:+")
}

sourceSets.test {
    compileClasspath += sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().compileClasspath
}

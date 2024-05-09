pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://devrepo.kakao.com/nexus/content/groups/public/")
    }
}

rootProject.name = "GodLife"
include(
    ":app",

    ":core:data",
    ":core:domain",
    "core:designsystem",
    ":core:network",
    ":core:sharedpreference",

    ":feature:main",
    ":feature:login",
    ":feature:main-page",
    ":feature:community-page",
    ":feature:setting-page",
)

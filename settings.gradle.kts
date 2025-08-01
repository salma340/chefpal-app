pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        google()
        jcenter()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()          // لازم يكون موجود
        mavenCentral()
        jcenter()         // ممكن تحذفيه لو مش محتاجاه، لكن مش غلط وجوده
    }
}


rootProject.name = "My ApplicationBNV"
include(":app")
 
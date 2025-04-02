pluginManagement {
    includeBuild("buildlogic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "BookPlayFree"

include(":app")
include(":shared")
include(":data")
include(":domain")
include(":core")
include(":core:designsystem")
include(":core:navigation")
include(":core:network")
include(":core:uicomponent")
include(":feature")
include(":feature:dashboard")
include(":feature:splashscreen")

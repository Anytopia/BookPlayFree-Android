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
include(":core")
include(":core:uicomponent")
include(":core:navigation")
include(":data")
include(":domain")
include(":feature")
include(":feature:splashscreen")
include(":shared")
include(":feature:dashboard")
include(":core:designsystem")

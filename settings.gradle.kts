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

gradle.startParameter.excludedTaskNames.addAll(listOf(":buildlogic:testClasses"))

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "BookPlayFree"

include(":app")
include(":shared")
include(":data")
include(":domain")
include(":core")
include(":core:ailocal")
include(":core:datastore")
include(":core:designsystem")
include(":core:firebase")
include(":core:navigation")
include(":core:network")
include(":core:test")
include(":core:uicomponent")
include(":core:utils")
include(":feature")
include(":feature:dashboard")
include(":feature:splashscreen")

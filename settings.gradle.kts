rootProject.name = "finisterra"
include("client", "server", "shared", "server:components", "client:components", "shared:components")
findProject(":shared:components")?.name = "shared-components"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
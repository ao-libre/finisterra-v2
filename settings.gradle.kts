rootProject.name = "finisterra"
include("client", "server", "shared", "server:components", "client:components", "shared:components")
findProject(":shared:components")?.name = "shared-components"
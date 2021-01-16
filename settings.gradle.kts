rootProject.name = "finisterra"
include("server", "server:components", "shared", "shared:components")
//include("client", "server", "shared", "server:components", "client:components", "shared:components")
findProject(":shared:components")?.name = "shared-components"
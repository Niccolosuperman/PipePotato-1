package module.roles

import ninja.leaping.configurate.objectmapping.Setting
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable

@ConfigSerializable
data class RolePermissions(
    @Setting val managePlayerLogins: Boolean = false
)

@ConfigSerializable
data class RoleProperties(
    @Setting val chatColor: String = "WHITE"
)

@ConfigSerializable
data class Role(
    @Setting val name: String = "Player",
    @Setting val priority: Int = 0,
    @Setting val properties: RoleProperties = RoleProperties(),
    @Setting val permissions: RolePermissions = RolePermissions()
)

@ConfigSerializable
data class Config(
    @Setting val default: String = "Player",
    @Setting val roles: List<Role> = listOf(Role())
)

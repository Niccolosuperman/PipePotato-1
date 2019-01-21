package io.github.pipespotatos

import module.roles.RolesConfig
import ninja.leaping.configurate.objectmapping.Setting
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable

@ConfigSerializable
data class Config(
    @Setting val roles: RolesConfig = RolesConfig()
)
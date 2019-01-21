package io.github.pipespotatos

import io.github.pipespotatos.module.auth.AuthConfig
import io.github.pipespotatos.module.auth.TpaConfig
import module.roles.RolesConfig
import ninja.leaping.configurate.objectmapping.Setting
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable

@ConfigSerializable
data class Config(
    @Setting val auth: AuthConfig = AuthConfig(),
    @Setting val chat: ChatConfig = ChatConfig(),
    @Setting val roles: RolesConfig = RolesConfig(),
    @Setting val tpa: TpaConfig = TpaConfig(),
    @Setting val messages: GlobalMessages = GlobalMessages()
)

@ConfigSerializable
data class ChatConfig(
    @Setting val prefix: String = "&e[&bPipe&6Potato&e]",
    @Setting val messageFormat: String = "%prefix% %message%",
    @Setting val exceptionFormat: String = "%prefix% &c%message%"
)

@ConfigSerializable
data class GlobalMessages(
    @Setting val onlyPlayers: String = "Only players can use this!",
    @Setting val notEligible: String = "You are not permitted to use this!"
)
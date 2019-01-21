package io.github.pipespotatos.module.auth

import ninja.leaping.configurate.objectmapping.Setting
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable

@ConfigSerializable
data class TpaConfig (
    @Setting val messages: TpaMessages = TpaMessages(),
    @Setting val cooldown: Long = 18L
)

@ConfigSerializable
data class TpaMessages(
    @Setting val noTpRequest: String = "There is no teleport request.",
    @Setting val tpRequestAccepted: String = "Teleport request is accepted.",
    @Setting val tpRequestDenied: String = "Teleport request is denied.",
    @Setting val playerRequestsTp: String = "%player% wants to teleport you, use /tpaccept to accept or deny with /tpdeny in 5 seconds.",
    @Setting val tpRequestSent: String = "Your teleport request is sent."
)

package io.github.pipespotatos.module.auth.player

import org.spongepowered.api.entity.living.player.Player

object AuthPlayerManager {

    private val players = mutableSetOf<AuthPlayer>()

    fun addPlayer(player: AuthPlayer) {
        players.add(player)
    }

    fun getPlayer(player: Player) =
        players.stream().findFirst().filter { authWallPlayer -> authWallPlayer.uuid == player.uniqueId }.orElse(
            AuthPlayer(player.uniqueId)
        )!!

}
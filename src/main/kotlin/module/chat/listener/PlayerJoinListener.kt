package io.github.pipespotatos.module.chat.listener

import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.filter.cause.First
import org.spongepowered.api.event.network.ClientConnectionEvent
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors

class PlayerJoinListener {

    @Listener
    fun onJoin(e: ClientConnectionEvent.Join, @First p: Player) {
        e.setMessage(Text.of(TextColors.GREEN, "+ ", plugin.getPlayerName(p)))
    }

}
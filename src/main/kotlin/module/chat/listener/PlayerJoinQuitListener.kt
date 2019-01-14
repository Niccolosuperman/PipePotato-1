package io.github.pipespotatos.module.chat.listener

import io.github.pipespotatos.module.chat.getPlayerName
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.filter.cause.First
import org.spongepowered.api.event.network.ClientConnectionEvent
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors

class PlayerJoinQuitListener {

    @Listener
    fun onJoin(e: ClientConnectionEvent.Join, @First p: Player) {
        e.setMessage(Text.of(TextColors.GREEN, " + ", getPlayerName(p)))
    }

    @Listener
    fun onPlayerQuit(e: ClientConnectionEvent.Disconnect, @First p: Player) {
        e.setMessage(Text.of(TextColors.RED, " - ", getPlayerName(p)))
    }

}


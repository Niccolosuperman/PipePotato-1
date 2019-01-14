package io.github.pipespotatos.module.chat.listener

import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.filter.cause.First
import org.spongepowered.api.event.network.ClientConnectionEvent
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors

class PlayerLeaveListener {

    @Listener
    fun onLeave(e: ClientConnectionEvent.Disconnect, @First p: Player) {
        e.setMessage(Text.of(TextColors.RED, "- ", plugin.getPlayerName(p)))
    }

}
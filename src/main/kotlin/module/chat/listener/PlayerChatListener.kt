package io.github.pipespotatos.module.chat.listener

import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.filter.cause.First
import org.spongepowered.api.event.message.MessageChannelEvent
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors


class PlayerChatListener {

    @Listener
    fun onChat(e: MessageChannelEvent.Chat, @First p: Player) {
        e.setMessage(Text.of(plugin.getPlayerName(p), TextColors.RESET, " ", e.rawMessage))
    }

}
package io.github.pipespotatos.module.auth.listener

import io.github.pipespotatos.module.auth.player.AuthPlayerManager
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.block.ChangeBlockEvent
import org.spongepowered.api.event.command.SendCommandEvent
import org.spongepowered.api.event.entity.MoveEntityEvent
import org.spongepowered.api.event.message.MessageChannelEvent
import org.spongepowered.api.text.Text

class PlayerActionListener {

    @Listener
    fun onPlayerChat(event: MessageChannelEvent.Chat) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onPlayerChangeBlock(event: ChangeBlockEvent) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onPlayerMove(event: MoveEntityEvent) {
        if (event.targetEntity is Player) {
            val player = event.targetEntity as Player

            if (!AuthPlayerManager.getPlayer(player).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onPlayerSendCommand(event: SendCommandEvent) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged) {
                val authWallCommands = arrayOf("login", "l", "register", "reg")
                event.isCancelled = true
                authWallCommands.forEach { command ->
                    if (event.command.equals(command, true))
                        event.isCancelled = false
                }
            }
            if (event.isCancelled)
                it.sendMessage(Text.of("You must login before using any other commands!"))
        }
    }


}
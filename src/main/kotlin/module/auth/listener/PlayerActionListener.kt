package io.github.pipespotatos.module.auth.listener

import io.github.pipespotatos.module.auth.player.AuthPlayerManager
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.action.FishingEvent
import org.spongepowered.api.event.action.InteractEvent
import org.spongepowered.api.event.block.ChangeBlockEvent
import org.spongepowered.api.event.block.tileentity.ChangeSignEvent
import org.spongepowered.api.event.command.SendCommandEvent
import org.spongepowered.api.event.data.ChangeDataHolderEvent
import org.spongepowered.api.event.entity.AttackEntityEvent
import org.spongepowered.api.event.entity.DamageEntityEvent
import org.spongepowered.api.event.entity.MoveEntityEvent
import org.spongepowered.api.event.item.inventory.ChangeInventoryEvent
import org.spongepowered.api.event.item.inventory.DropItemEvent
import org.spongepowered.api.event.item.inventory.InteractInventoryEvent
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

    @Listener
    fun onPlayerChangeInventory(event: ChangeInventoryEvent) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onPlayerInteract(event: InteractEvent) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onPlayerChangeDataHolder(event: ChangeDataHolderEvent) {
        if (event.targetHolder is Player) {
            val player = event.targetHolder as Player
            if (!AuthPlayerManager.getPlayer(player).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onPlayerInteractInventory(event: InteractInventoryEvent) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onPlayerAttackEntity(event: AttackEntityEvent) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onDamagePlayer(event: DamageEntityEvent) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onPlayerDropItem(event: DropItemEvent) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onPlayerChangeSign(event: ChangeSignEvent) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged)
                event.isCancelled = true
        }
    }

    @Listener
    fun onPlayerFishing(event: FishingEvent.Start) {
        event.cause.first(Player::class.java).ifPresent {
            if (!AuthPlayerManager.getPlayer(it).isLogged)
                event.isCancelled = true
        }
    }


}
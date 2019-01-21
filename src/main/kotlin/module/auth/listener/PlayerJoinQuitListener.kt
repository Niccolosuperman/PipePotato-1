package io.github.pipespotatos.module.auth.listener

import io.github.pipespotatos.Config
import io.github.pipespotatos.PipePotatoPlugin
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.extensions.sendMessage
import io.github.pipespotatos.module.auth.NotLoggedException
import io.github.pipespotatos.module.auth.player.AuthPlayerManager
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.network.ClientConnectionEvent
import org.spongepowered.api.scheduler.Task
import org.spongepowered.api.text.serializer.TextSerializers
import java.util.concurrent.TimeUnit

class PlayerJoinQuitListener {

    private val plugin = ModuleManager.getClass<PipePotatoPlugin>()
    private val config = ModuleManager.getClass<Config>()

    @Listener
    fun onPlayerJoin(event: ClientConnectionEvent.Join) {
        val player = event.targetEntity
        val authWallPlayer = AuthPlayerManager.getPlayer(player)

        if (!authWallPlayer.isRegistered)
            player.sendMessage(config.auth.messages.register)
        else
            player.sendMessage(config.auth.messages.login)

        Task.builder().execute(Runnable {
            if (!authWallPlayer.isLogged) {
                player.kick(TextSerializers.FORMATTING_CODE.deserialize(config.auth.messages.timeout))
            }
        }).delay(config.auth.timeout, TimeUnit.SECONDS).submit(plugin)
    }

    @Listener
    fun onPlayerQuit(event: ClientConnectionEvent.Disconnect) {
        try {
            AuthPlayerManager.getPlayer(event.targetEntity).logout()
        } catch (exception: NotLoggedException) {
            // User wasn't able to login successfully or unregistered while on server and quit
        }
    }

}
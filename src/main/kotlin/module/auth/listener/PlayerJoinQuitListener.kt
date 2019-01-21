package io.github.pipespotatos.module.auth.listener

import io.github.pipespotatos.PipePotatoPlugin
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.module.auth.NotLoggedException
import io.github.pipespotatos.module.auth.player.AuthPlayerManager
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.network.ClientConnectionEvent
import org.spongepowered.api.scheduler.Task
import org.spongepowered.api.text.Text
import java.util.concurrent.TimeUnit

class PlayerJoinQuitListener {

    private val plugin = ModuleManager.getClass<PipePotatoPlugin>()

    @Listener
    fun onPlayerJoin(event: ClientConnectionEvent.Join) {
        val player = event.targetEntity
        val authWallPlayer = AuthPlayerManager.getPlayer(player)

        if (!authWallPlayer.isRegistered)
            player.sendMessage(Text.of("You are not registered, register via /register <password> <verify>"))
        else
            player.sendMessage(Text.of("You must log in. Usage is /login <password>"))

        Task.builder().execute(Runnable {
            if (!authWallPlayer.isLogged) {
                player.kick()
            }
        }).delay(10, TimeUnit.SECONDS).submit(plugin) // @todo Config
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
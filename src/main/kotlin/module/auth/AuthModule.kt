package io.github.pipespotatos.module.auth

import io.github.pipespotatos.api.module.Module
import io.github.pipespotatos.module.auth.command.CommandSpecs
import io.github.pipespotatos.module.auth.listener.PlayerActionListener
import io.github.pipespotatos.module.auth.listener.PlayerJoinQuitListener
import org.spongepowered.api.Sponge

class AuthModule : Module("auth", "Auth") {

    override fun onEnable() {
        registerCommands()
        registerEvents()

        logger.info("Module is enabled.")
    }

    override fun onDisable() {
        logger.info("Module is disabled.")
    }

    private fun registerCommands() {
        val commandManager = Sponge.getCommandManager()

        commandManager.register(this, CommandSpecs.getLoginCommandSpec(), "login", "l")
        commandManager.register(this, CommandSpecs.getRegisterCommandSpec(), "register", "reg")
    }

    private fun registerEvents() {
        val eventManager = Sponge.getEventManager()

        eventManager.registerListeners(this, PlayerActionListener())
        eventManager.registerListeners(this, PlayerJoinQuitListener())
    }

}
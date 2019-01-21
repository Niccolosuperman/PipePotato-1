package io.github.pipespotatos.module.chat

import io.github.pipespotatos.PipePotatoPlugin
import io.github.pipespotatos.api.module.Module
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.module.chat.listener.PlayerChatListener
import io.github.pipespotatos.module.chat.listener.PlayerJoinQuitListener
import org.spongepowered.api.Sponge


class ChatModule : Module("chat", "Chat") {

    private val plugin = ModuleManager.getClass<PipePotatoPlugin>()

    override fun onEnable() {
        registerEvents()

        logger.info("Module is enabled.")
    }

    override fun onDisable() {
        logger.info("Module is disabled.")
    }

    private fun registerEvents() {
        val eventManager = Sponge.getEventManager()

        eventManager.registerListeners(plugin, PlayerChatListener())
        eventManager.registerListeners(plugin, PlayerJoinQuitListener())
    }

}
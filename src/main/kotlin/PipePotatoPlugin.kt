package io.github.pipespotatos

import com.google.inject.Inject
import io.github.pipespotatos.api.config.loadConfig
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.module.auth.AuthModule
import io.github.pipespotatos.module.chat.ChatModule
import io.github.pipespotatos.module.tpa.TpaModule
import module.roles.RoleModule
import org.slf4j.Logger
import org.spongepowered.api.event.Listener
import org.spongepowered.api.event.game.state.GameStartedServerEvent
import org.spongepowered.api.event.game.state.GameStoppedServerEvent
import org.spongepowered.api.plugin.Plugin
import java.io.File


@Plugin(
    version = "0.2.2",
    id = "pipepotato",
    name = "PipePotato",
    authors = ["Pipes & Potatoes"],
    url = "https://pipespotatoes.github.io",
    description = "Core plugin which features all the essentials and mechanics of the official server"
)
class PipePotatoPlugin {

    @Inject
    private lateinit var logger: Logger

    @Listener
    fun onServerStart(event: GameStartedServerEvent) {
        ModuleManager.registerClass(this)
        ModuleManager.registerClass(loadConfig<Config>(File("./config/pipes.conf")))
        ModuleManager.registerModule(AuthModule())
        ModuleManager.registerModule(ChatModule())
        ModuleManager.registerModule(RoleModule())
        ModuleManager.registerModule(TpaModule())
        ModuleManager.startAllModules()

        logger.info("Core plugin enabled.")
    }

    @Listener
    fun onServerStop(event: GameStoppedServerEvent) {
        ModuleManager.stopAllModules()

        logger.info("Core plugin disabled.")
    }

}

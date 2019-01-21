package io.github.pipespotatos.module.tpa.command

import io.github.pipespotatos.Config
import io.github.pipespotatos.PipePotatoPlugin
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.extensions.sendException
import io.github.pipespotatos.extensions.sendMessage
import io.github.pipespotatos.module.tpa.TpaModule
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.scheduler.Task
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors
import java.util.concurrent.TimeUnit

class TpaCommand(private val module: TpaModule) : CommandExecutor {

    private val plugin = ModuleManager.getClass<PipePotatoPlugin>()
    val config = ModuleManager.getClass<Config>()

    override fun execute(src: CommandSource, args: CommandContext): CommandResult {
        if (src !is Player) {
            src.sendException(config.messages.onlyPlayers)
            return CommandResult.empty()
        }

        val to = args.getOne<Player>("to").get()

        module.tpList[to] = src

        to.sendMessage(config.tpa.messages.playerRequestsTp.replace("%player%", src.name))
        src.sendMessage(config.tpa.messages.tpRequestSent)

        Task.builder().execute(Runnable {
            if (module.tpList.containsKey(to)) {
                module.tpList.remove(to)
            }
        }).delay(5, TimeUnit.SECONDS).submit(plugin)

        return CommandResult.success()
    }

}
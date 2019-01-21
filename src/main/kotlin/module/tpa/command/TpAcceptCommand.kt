package io.github.pipespotatos.module.tpa.command

import io.github.pipespotatos.Config
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.extensions.sendException
import io.github.pipespotatos.extensions.sendMessage
import io.github.pipespotatos.module.tpa.TpaModule
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player

class TpAcceptCommand(private val module: TpaModule) : CommandExecutor {
    override fun execute(src: CommandSource, args: CommandContext): CommandResult {
        val config = ModuleManager.getClass<Config>()

        if (src !is Player) {
            src.sendException(config.messages.onlyPlayers)
            return CommandResult.empty()
        }

        if (!module.tpList.containsKey(src)) {
            src.sendException(config.tpa.messages.noTpRequest)
            return CommandResult.empty()
        }
        module.tpList[src]!!.sendMessage("&e${config.tpa.messages.tpRequestAccepted}")
        src.sendMessage("&e${config.tpa.messages.tpRequestAccepted}")

        module.tpList[src]!!.location = src.location
        module.tpList.remove(src)

        return CommandResult.success()
    }
}
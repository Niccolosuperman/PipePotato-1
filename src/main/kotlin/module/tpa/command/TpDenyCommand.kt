package io.github.pipespotatos.module.tpa.command

import io.github.pipespotatos.Config
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.extensions.sendException
import io.github.pipespotatos.module.tpa.TpaModule
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player

class TpDenyCommand(private val module: TpaModule) : CommandExecutor {
    val config = ModuleManager.getClass<Config>()

    override fun execute(src: CommandSource, args: CommandContext): CommandResult {
        if (src !is Player) {
            src.sendException(config.messages.onlyPlayers)
            return CommandResult.empty()
        }

        if (!module.tpList.containsKey(src)) {
            src.sendException(config.tpa.messages.noTpRequest)
            return CommandResult.empty()
        }

        module.tpList[src]!!.sendException(config.tpa.messages.tpRequestDenied)
        src.sendException(config.tpa.messages.tpRequestDenied)

        module.tpList.remove(src)

        return CommandResult.success()
    }
}
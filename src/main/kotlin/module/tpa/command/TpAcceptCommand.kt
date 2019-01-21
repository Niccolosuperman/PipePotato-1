package io.github.pipespotatos.module.tpa.command

import io.github.pipespotatos.module.tpa.TpaModule
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors

class TpAcceptCommand(private val module: TpaModule) : CommandExecutor {
    override fun execute(src: CommandSource, args: CommandContext): CommandResult {
        if (src !is Player) {
            src.sendMessage(Text.of(TextColors.RED, "Bu komut oyuncular içindir"))
            return CommandResult.empty()
        }

        if (!module.tpList.containsKey(src)) {
            src.sendMessage(Text.of(TextColors.RED, "Bir ışınlanma isteği bulunmuyor"))
            return CommandResult.empty()
        }

        module.tpList[src]!!.sendMessage(Text.of(TextColors.GREEN, "Işınlanma isteği onaylanmıştır"))
        src.sendMessage(Text.of(TextColors.GREEN, "Işınlanma isteği onaylanmıştır"))

        module.tpList[src]!!.location = src.location
        module.tpList.remove(src)

        return CommandResult.success()
    }
}
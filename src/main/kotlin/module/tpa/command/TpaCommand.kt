package io.github.pipespotatos.module.tpa.command

import io.github.pipespotatos.PipePotatoPlugin
import io.github.pipespotatos.api.module.ModuleManager
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

    override fun execute(src: CommandSource, args: CommandContext): CommandResult {
        if (src !is Player) {
            src.sendMessage(Text.of(TextColors.RED, "Bu komut oyuncular içindir"))
            return CommandResult.empty()
        }

        val to = args.getOne<Player>("to").get()

        module.tpList[to] = src

        to.sendMessage(
            Text.of(
                TextColors.RED,
                src.name,
                TextColors.GOLD,
                " size ışınlanmak istiyor. Onaylamak için ",
                TextColors.GREEN,
                "/tpaccept ",
                TextColors.GOLD,
                " reddetmek için ",
                TextColors.DARK_RED,
                "/tpdeny",
                TextColors.GOLD,
                " yazmalısınız.\n5 saniye içinde bu işlem iptal edilecektir"
            )
        )
        src.sendMessage(Text.of(TextColors.GREEN, "Işınlanma isteğiniz gönderilmiştir..."))

        Task.builder().execute(Runnable {
            if (module.tpList.containsKey(to)) {
                module.tpList.remove(to)
            }
        }).delay(5, TimeUnit.SECONDS).submit(plugin)

        return CommandResult.success()
    }

}
package io.github.pipespotatos.module.auth.command

import io.github.pipespotatos.Config
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.extensions.sendException
import io.github.pipespotatos.extensions.sendMessage
import io.github.pipespotatos.module.auth.player.AuthPlayerManager
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text

class RegisterExecutor : CommandExecutor {

    private val config = ModuleManager.getClass<Config>()

    override fun execute(source: CommandSource, args: CommandContext): CommandResult {
        if (source is Player) {
            val authWallPlayer = AuthPlayerManager.getPlayer(source)
            val password = args.getOne<String>("password")
            val verify = args.getOne<String>("verify")

            if (password.isPresent && verify.isPresent) {
                try {
                    authWallPlayer.register(password.get(), verify.get())

                    source.sendMessage(config.auth.messages.successfulRegister)

                    return CommandResult.success()
                } catch (exception: Exception) {
                    exception.message?.let { source.sendException(it) }
                }
            }
        } else
            source.sendException(config.messages.onlyPlayers)
        return CommandResult.empty()
    }

}
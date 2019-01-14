package io.github.pipespotatos.module.auth.command

import io.github.pipespotatos.module.auth.AlreadyLoggedException
import io.github.pipespotatos.module.auth.IncorrectPasswordException
import io.github.pipespotatos.module.auth.player.AuthPlayerManager
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text

class LoginExecutor : CommandExecutor {

    override fun execute(source: CommandSource, args: CommandContext): CommandResult {
        var ret = CommandResult.empty()

        if (source is Player) {
            val authWallPlayer = AuthPlayerManager.getPlayer(source)
            val password = args.getOne<String>("password")

            password.ifPresent {
                if (authWallPlayer.isRegistered)
                    try {
                        authWallPlayer.login(it)

                        source.sendMessage(Text.of("You logged in successfully!"))

                        ret = CommandResult.success()
                    } catch (exception: AlreadyLoggedException) {
                        source.sendMessage(Text.of("You are already logged in!"))
                    } catch (exception: IncorrectPasswordException) {
                        source.sendMessage(Text.of("Incorrect password!"))
                    }
                else
                    source.sendMessage(Text.of("You must register first!"))
            }
        } else
            source.sendMessage(Text.of("Only players can use this command!"))
        return ret
    }

}
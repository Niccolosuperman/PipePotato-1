package io.github.pipespotatos.module.auth.command

import io.github.pipespotatos.module.auth.AlreadyLoggedException
import io.github.pipespotatos.module.auth.AlreadyRegisteredException
import io.github.pipespotatos.module.auth.PasswordsDontMatchException
import io.github.pipespotatos.module.auth.player.AuthPlayerManager
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text

class RegisterExecutor : CommandExecutor {

    override fun execute(source: CommandSource, args: CommandContext): CommandResult {
        if (source is Player) {
            val authWallPlayer = AuthPlayerManager.getPlayer(source)
            val password = args.getOne<String>("password")
            val verify = args.getOne<String>("verify")

            if (password.isPresent && verify.isPresent) {
                try {
                    authWallPlayer.register(password.get(), verify.get())

                    source.sendMessage(Text.of("You registered successfully!"))

                    return CommandResult.success()
                } catch (exception: AlreadyRegisteredException) {
                    source.sendMessage(Text.of("You are already registered!"))
                } catch (exception: AlreadyLoggedException) { // It is technically impossible to catch this but you never know
                    source.sendMessage(Text.of("You must logout before registering!"))
                } catch (exception: PasswordsDontMatchException) {
                    source.sendMessage(Text.of("Passwords doesn't match!"))
                }
            }
        } else
            source.sendMessage(Text.of("Only players can use this command!"))
        return CommandResult.empty()
    }

}
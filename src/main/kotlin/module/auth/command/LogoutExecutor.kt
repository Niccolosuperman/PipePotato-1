package io.github.pipespotatos.module.auth.command

import io.github.pipespotatos.module.auth.NotLoggedException
import io.github.pipespotatos.module.auth.player.AuthPlayerManager
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text

class LogoutExecutor : CommandExecutor {

    override fun execute(source: CommandSource, args: CommandContext): CommandResult {
        if (source is Player) {
            val authWallPlayer = AuthPlayerManager.getPlayer(source)

            try {
                authWallPlayer.logout()

                source.sendMessage(Text.of("You logged out successfully!"))

                return CommandResult.success()
            } catch (exception: NotLoggedException) {
                source.sendMessage(Text.of("You must login before logging out!"))
            }
        } else
            source.sendMessage(Text.of("Only players can use this command!"))

        return CommandResult.empty()
    }

}
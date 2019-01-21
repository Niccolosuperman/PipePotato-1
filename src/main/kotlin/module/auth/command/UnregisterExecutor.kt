package io.github.pipespotatos.module.auth.command

import io.github.pipespotatos.module.auth.NotRegisteredException
import io.github.pipespotatos.module.auth.player.AuthPlayerManager
import io.github.pipespotatos.module.roles.RoleManager
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text

class UnregisterExecutor : CommandExecutor {

    override fun execute(source: CommandSource, args: CommandContext): CommandResult {
        var ret = CommandResult.empty()

        if (source is Player) {
            if (!RoleManager.playerHasPermission(source.uniqueId, "managePlayerLogins")) {
                source.sendMessage(Text.of("You cannot do this"))
                return CommandResult.empty()
            }
        }

        args.getOne<Player>("target").ifPresent {
            val authWallPlayer = AuthPlayerManager.getPlayer(it)

            try {
                authWallPlayer.unregister()

                source.sendMessage(Text.of("Player unregistered successfully!"))
                it.sendMessage(Text.of("You are unregistered!"))

                ret = CommandResult.success()
            } catch (exception: NotRegisteredException) {
                source.sendMessage(Text.of("Target is not registered!"))
            }
        }

        return ret
    }

}
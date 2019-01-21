package io.github.pipespotatos.module.auth.command

import io.github.pipespotatos.Config
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.extensions.sendException
import io.github.pipespotatos.extensions.sendMessage
import io.github.pipespotatos.module.auth.player.AuthPlayerManager
import io.github.pipespotatos.module.roles.RoleManager
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player

class UnregisterExecutor : CommandExecutor {

    private val config = ModuleManager.getClass<Config>()

    override fun execute(source: CommandSource, args: CommandContext): CommandResult {
        var ret = CommandResult.empty()

        if (source is Player) {
            if (!RoleManager.playerHasPermission(source.uniqueId, "managePlayerLogins")) {
                source.sendException(config.messages.notEligible)
                return CommandResult.empty()
            }
        }

        args.getOne<Player>("target").ifPresent {
            val authWallPlayer = AuthPlayerManager.getPlayer(it)

            try {
                authWallPlayer.unregister()

                source.sendMessage(config.auth.messages.successfulUnregister)
                it.sendMessage(config.auth.messages.successfulTargetUnregister)

                ret = CommandResult.success()
            } catch (exception: Exception) {
                exception.message?.let { source.sendException(it) }
            }
        }

        return ret
    }

}
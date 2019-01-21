package io.github.pipespotatos.module.roles.command

import io.github.pipespotatos.Config
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.extensions.sendException
import io.github.pipespotatos.extensions.sendMessage
import io.github.pipespotatos.module.roles.RoleManager
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player

class DelRoleCommand : CommandExecutor {
    override fun execute(src: CommandSource, args: CommandContext): CommandResult {
        val config = ModuleManager.getClass<Config>()
        val p = args.getOne<Player>("player").get()
        val role = args.getOne<String>("role").get()
        val roleObj = RoleManager.config.roles.find { it.name == role }

        if (roleObj == null) {
            src.sendException(config.roles.messages.roleNotFound)
            return CommandResult.empty()
        }

        if (src is Player) {
            var highestPriority = 0
            RoleManager.getPlayerRoles(src.uniqueId)
                .forEach { if (it.priority > highestPriority) highestPriority = it.priority }

            if (roleObj.priority > highestPriority) {
                src.sendException(config.messages.notEligible)
                return CommandResult.empty()
            }
        }

        val roles = RoleManager.getPlayerRoles(p.uniqueId).toMutableList()

        if (roleObj !in roles) {
            src.sendException(config.roles.messages.doesntHaveRole)
            return CommandResult.empty()
        }

        roles.remove(roleObj)
        RoleManager.setPlayerRoles(p.uniqueId, roles)

        src.sendMessage(config.roles.messages.success)
        return CommandResult.success()
    }
}
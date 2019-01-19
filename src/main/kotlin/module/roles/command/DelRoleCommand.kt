package io.github.pipespotatos.module.roles.command

import io.github.pipespotatos.module.roles.RoleManager
import org.spongepowered.api.command.CommandResult
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.command.args.CommandContext
import org.spongepowered.api.command.spec.CommandExecutor
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.format.TextColors

class DelRoleCommand : CommandExecutor {
    override fun execute(src: CommandSource, args: CommandContext): CommandResult {
        val p = args.getOne<Player>("player").get()
        val role = args.getOne<String>("role").get()
        val roleObj = RoleManager.config.roles.find { it.name == role }

        if (roleObj == null) {
            src.sendMessage(Text.join(Text.of(TextColors.RED, "Role not found")))
            return CommandResult.empty()
        }

        if (src is Player) {
            var highestPriority = 0
            RoleManager.getPlayerRoles(src.uniqueId)
                .forEach { if (it.priority > highestPriority) highestPriority = it.priority }

            if (roleObj.priority > highestPriority) {
                src.sendMessage(Text.join(Text.of(TextColors.RED, "You don't have enough permissions to remove this role")))
                return CommandResult.empty()
            }
        }

        val roles = RoleManager.getPlayerRoles(p.uniqueId).toMutableList()

        if (roleObj !in roles) {
            src.sendMessage(Text.join(Text.of(TextColors.RED, "Player doesn't have that role")))
            return CommandResult.empty()
        }

        roles.remove(roleObj)
        RoleManager.setPlayerRoles(p.uniqueId, roles)

        src.sendMessage(Text.join(Text.of(TextColors.GREEN, "Done!")))
        return CommandResult.success()
    }
}
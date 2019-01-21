package module.roles

import io.github.pipespotatos.Config
import io.github.pipespotatos.PipePotatoPlugin
import io.github.pipespotatos.api.module.Module
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.module.roles.RoleManager
import io.github.pipespotatos.module.roles.command.AddRoleCommand
import io.github.pipespotatos.module.roles.command.DelRoleCommand
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.args.GenericArguments
import org.spongepowered.api.command.spec.CommandSpec
import org.spongepowered.api.text.Text

class RoleModule : Module("role", "Role") {

    private val plugin = ModuleManager.getClass<PipePotatoPlugin>()
    private val config = ModuleManager.getClass<Config>()

    override fun onEnable() {
        config()
        commands()
    }

    private fun config() {
        RoleManager.setConfig(config.roles)
        this.logger.info("Loaded ${config.roles.roles.size} roles!")
    }

    private fun commands() {
        val commandManager = Sponge.getCommandManager()

        commandManager.register(plugin, CommandSpec.builder().apply {
            description(Text.of("Add role to player"))
            permission("roles.admin.add")
            arguments(
                GenericArguments.playerOrSource(Text.of("player")),
                GenericArguments.string(Text.of("role"))
            )
            executor(AddRoleCommand())
        }.build(), "addrole")

        commandManager.register(plugin, CommandSpec.builder().apply {
            description(Text.of("Remove role from player"))
            permission("roles.admin.delete")
            arguments(
                GenericArguments.playerOrSource(Text.of("player")),
                GenericArguments.string(Text.of("role"))
            )
            executor(DelRoleCommand())
        }.build(), "delrole")
    }
}
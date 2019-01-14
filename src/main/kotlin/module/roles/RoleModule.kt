package module.roles

import com.google.common.reflect.TypeToken
import io.github.pipespotatos.PipePotatoPlugin
import io.github.pipespotatos.api.module.Module
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.extensions.ifNull
import io.github.pipespotatos.module.roles.RoleManager
import io.github.pipespotatos.module.roles.command.AddRoleCommand
import io.github.pipespotatos.module.roles.command.DelRoleCommand
import ninja.leaping.configurate.hocon.HoconConfigurationLoader
import ninja.leaping.configurate.objectmapping.ObjectMappingException
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.args.GenericArguments
import org.spongepowered.api.command.spec.CommandSpec
import org.spongepowered.api.text.Text
import java.io.File
import java.io.IOException

class RoleModule : Module("role", "Role") {

    private val plugin = ModuleManager.getClass<PipePotatoPlugin>()
    private lateinit var config: Config

    override fun onEnable() {
        config()
        commands()
    }

    private fun config() {
        val configManager = HoconConfigurationLoader.builder().setPath(File("./roles.conf").toPath()).build()

        try {
            this.config = configManager.load().getValue(TypeToken.of(Config::class.java)).ifNull {
                this.logger.info("Config is null, generating...")

                val node = configManager.createEmptyNode()
                node.setValue(TypeToken.of(Config::class.java), Config())

                configManager.save(node)

                Config()
            }
        } catch (e: ObjectMappingException) {
            this.logger.error("Failed to load the config - Using a default", e)
            this.config = Config()
        } catch (e: IOException) {
            this.logger.error("Failed to load the config - Using a default", e)
            this.config = Config()
        }

        RoleManager.setConfig(this.config)
        this.logger.info("Loaded ${this.config.roles.size} roles!")
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
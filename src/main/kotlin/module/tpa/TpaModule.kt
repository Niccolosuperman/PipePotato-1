package io.github.pipespotatos.module.tpa

import io.github.pipespotatos.PipePotatoPlugin
import io.github.pipespotatos.api.module.Module
import io.github.pipespotatos.api.module.ModuleManager
import io.github.pipespotatos.module.tpa.command.TpAcceptCommand
import io.github.pipespotatos.module.tpa.command.TpDenyCommand
import io.github.pipespotatos.module.tpa.command.TpaCommand
import org.spongepowered.api.Sponge
import org.spongepowered.api.command.args.GenericArguments
import org.spongepowered.api.command.spec.CommandSpec
import org.spongepowered.api.entity.living.player.Player
import org.spongepowered.api.text.Text

class TpaModule : Module("tpa", "TPA") {

    private val plugin = ModuleManager.getClass<PipePotatoPlugin>()

    // Key => To
    // Value => From
    val tpList: HashMap<Player, Player> = hashMapOf()

    override fun onEnable() {
        commands()
    }

    private fun commands() {
        val commandManager = Sponge.getCommandManager()

        commandManager.register(this, CommandSpec.builder().apply {
            description(Text.of("Send a teleportation request"))
            arguments(
                GenericArguments.player(Text.of("player"))
            )
            executor(TpaCommand(this@TpaModule))
        }.build(), "tpa")

        commandManager.register(this, CommandSpec.builder().apply {
            description(Text.of("Accept a teleportation request"))
            executor(TpAcceptCommand(this@TpaModule))
        }.build(), "tpaccept")

        commandManager.register(this, CommandSpec.builder().apply {
            description(Text.of("Deny a teleportation request"))
            executor(TpDenyCommand(this@TpaModule))
        }.build(), "tpdeny")

    }

}
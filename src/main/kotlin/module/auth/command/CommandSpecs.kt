package io.github.pipespotatos.module.auth.command

import org.spongepowered.api.command.args.GenericArguments
import org.spongepowered.api.command.spec.CommandSpec
import org.spongepowered.api.text.Text

object CommandSpecs {

    fun getLoginCommandSpec(): CommandSpec = CommandSpec.builder()
        .description(Text.of("Login command for players"))
        .arguments(
            GenericArguments.onlyOne(GenericArguments.string(Text.of("password")))
        )
        .executor(LoginExecutor())
        .build()

    fun getLogoutCommandSpec(): CommandSpec = CommandSpec.builder()
        .description(Text.of("Logout command for players"))
        .executor(LogoutExecutor())
        .build()

    fun getRegisterCommandSpec(): CommandSpec = CommandSpec.builder()
        .description(Text.of("Registers a player to database"))
        .arguments(
            GenericArguments.onlyOne(GenericArguments.string(Text.of("password"))),
            GenericArguments.onlyOne(GenericArguments.string(Text.of("verify")))
        )
        .executor(RegisterExecutor())
        .build()

    fun getUnregisterCommandSpec(): CommandSpec = CommandSpec.builder()
        .description(Text.of("Unregisters a player"))
        .arguments(
            GenericArguments.onlyOne(GenericArguments.player(Text.of("target")))
        )
        .executor(UnregisterExecutor())
        .build()
}
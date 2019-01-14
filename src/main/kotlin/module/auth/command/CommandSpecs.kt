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

    fun getRegisterCommandSpec(): CommandSpec = CommandSpec.builder()
        .description(Text.of("Registers a player to database"))
        .arguments(
            GenericArguments.onlyOne(GenericArguments.string(Text.of("password"))),
            GenericArguments.onlyOne(GenericArguments.string(Text.of("verify")))
        )
        .executor(RegisterExecutor())
        .build()


}
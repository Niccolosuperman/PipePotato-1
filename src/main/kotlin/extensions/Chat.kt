package io.github.pipespotatos.extensions

import io.github.pipespotatos.Config
import io.github.pipespotatos.api.module.ModuleManager
import org.spongepowered.api.command.CommandSource
import org.spongepowered.api.text.Text
import org.spongepowered.api.text.serializer.TextSerializers

private val config = ModuleManager.getClass<Config>()

private val prefix = config.chat.prefix
private val messageFormat = config.chat.messageFormat
private val exceptionFormat = config.chat.exceptionFormat

fun CommandSource.sendMessage(message: String) {
    sendMessage(format(messageFormat, message))
}

fun CommandSource.sendException(message: String) {
    sendMessage(format(exceptionFormat, message))
}

private fun format(format: String, message: String): Text = TextSerializers.FORMATTING_CODE.deserialize(
    format
        .replace("%prefix%", prefix)
        .replace("%message%", message)
)
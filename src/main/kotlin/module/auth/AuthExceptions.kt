package io.github.pipespotatos.module.auth

import io.github.pipespotatos.Config
import io.github.pipespotatos.api.module.ModuleManager

private val messages = ModuleManager.getClass<Config>().auth.messages

class AlreadyLoggedException : Exception(messages.alreadyLogged)

class AlreadyRegisteredException : Exception(messages.alreadyRegistered)

class IncorrectPasswordException : Exception(messages.incorrectPassword)

class PasswordsDontMatchException : Exception(messages.passwordsDontMatch)

class NotLoggedException : Exception(messages.notLogged)

class NotRegisteredException : Exception(messages.notRegistered)
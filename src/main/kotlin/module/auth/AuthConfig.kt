package io.github.pipespotatos.module.auth

import ninja.leaping.configurate.objectmapping.Setting
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable

@ConfigSerializable
data class AuthConfig(
    @Setting val messages: AuthMessages = AuthMessages(),
    @Setting val timeout: Long = 18L
)

@ConfigSerializable
data class AuthMessages(
    @Setting val successfulLogin: String = "You registered successfully!",
    @Setting val successfulRegister: String = "You logged out successfully!",
    @Setting val successfulLogout: String = "You logged out successfully!",
    @Setting val successfulUnregister: String = "Player unregistered successfully!",
    @Setting val successfulTargetUnregister: String = "You are unregistered!",
    @Setting val incorrectPassword: String = "Incorrect password!",
    @Setting val passwordsDontMatch: String = "Passwords doesn't match!",
    @Setting val alreadyLogged: String = "You are already logged in!",
    @Setting val alreadyRegistered: String = "You are already registered!",
    @Setting val notRegistered: String = "You must register first!",
    @Setting val notLogged: String = "You must login before logging out!",
    @Setting val targetNotRegistered: String = "Target is not registered!",
    @Setting val loginBeforeCommand: String = "You must login before using any other commands!",
    @Setting val register: String = "You are not registered, register via /register <password> <verify>",
    @Setting val login: String = "You must log in. Usage is /login <password>"
)

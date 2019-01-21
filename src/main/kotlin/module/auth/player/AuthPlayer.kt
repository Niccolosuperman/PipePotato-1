package io.github.pipespotatos.module.auth.player

import at.favre.lib.crypto.bcrypt.BCrypt
import io.github.pipespotatos.module.auth.*
import org.spongepowered.api.Sponge
import java.util.*

class AuthPlayer(val uuid: UUID) {

    private var password: String? = null

    var isLogged = false
        private set

    var isRegistered = false
        private set

    fun login(password: String) {
        if (isLogged)
            throw AlreadyLoggedException()
        if (!isRegistered)
            throw NotRegisteredException()
        if (!comparePassword(password))
            throw IncorrectPasswordException()
        isLogged = true
    }

    fun logout() {
        if (!isLogged)
            throw NotLoggedException()
        isLogged = false
    }

    fun unregister() {
        if (!isRegistered)
            throw NotRegisteredException()
        isLogged = false
        isRegistered = false

        AuthManager.unregisterPlayer(uuid)
    }

    fun register(password: String, verify: String) {
        if (isRegistered)
            throw AlreadyRegisteredException()
        if (!verifyPassword(password, verify))
            throw PasswordsDontMatchException()

        val hashed = BCrypt.withDefaults().hashToString(12, password.toCharArray())
        AuthManager.registerPlayer(uuid, hashed)

        this.password = hashed
        isRegistered = true
        isLogged = true
    }

    init {
        isRegistered = AuthManager.isPlayerRegistered(uuid)
        if (isRegistered)
            password = AuthManager.getPlayerPassword(uuid)
        AuthPlayerManager.addPlayer(this)
    }

    fun getRawPlayer() = Sponge.getServer().getPlayer(uuid)

    private fun comparePassword(password: String) = BCrypt.verifyer().verify(password.toCharArray(), this.password).verified

    private fun verifyPassword(password: String, verify: String) = (verify == password)

}
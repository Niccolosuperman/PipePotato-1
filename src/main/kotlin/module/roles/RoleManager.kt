package io.github.pipespotatos.module.roles

import io.github.pipespotatos.api.database.Database
import io.github.pipespotatos.api.database.execute
import io.github.pipespotatos.api.database.executeUpdate
import io.github.pipespotatos.extensions.ifNull
import module.roles.Config
import module.roles.Role
import java.util.*


object RoleManager : Database("role") {

    private const val table = "roles"
    internal lateinit var config: Config

    init {
        createTableIfNotExists()
    }

    fun setConfig(c: Config) {
        this.config = c
    }

    private fun createTableIfNotExists() {
        getConnection().executeUpdate("CREATE TABLE IF NOT EXISTS $table (UUID varchar(255), Roles varchar(255));") {}
    }

    fun getPlayerRoleString(uuid: UUID): String {
        if (!isPlayerRegistered(uuid))
            return ""

        var ret = ""
        getConnection().execute("SELECT Roles FROM $table WHERE UUID=?;", {
            setString(1, uuid.toString())
        }, {
            while (next())
                ret = getString("Roles")
        })

        return ret
    }

    fun getPlayerRoles(uuid: UUID): List<Role> {
        val ret = getPlayerRoleString(uuid).split(",")

        return ret.map { name ->
            config.roles
                .find { it.name == name }
                .ifNull { config.roles.find { it.name == config.default }!! }
        }.sortedBy { it.priority }.distinct()
    }

    fun setPlayerRoles(uuid: UUID, roles: List<Role>) {
        getConnection().executeUpdate("MERGE INTO $table KEY (UUID) VALUES(?, ?);") {
            setString(1, uuid.toString())
            setString(2, roles.joinToString(",") { it.name })
        }
    }

    private fun isPlayerRegistered(uuid: UUID): Boolean {
        var ret = false
        getConnection().execute("SELECT * FROM $table WHERE UUID=?", {
            setString(1, uuid.toString())
        }, {
            while (next())
                ret = true
        })
        return ret
    }

    fun playerHasPermission(uuid: UUID, permission: String): Boolean {
        var ret = false

        getPlayerRoles(uuid).forEach {
            if (ret)
                return@forEach

            ret = it.permissions::class.java.getDeclaredField(permission).getBoolean(it.permissions)
        }

        return ret
    }

}
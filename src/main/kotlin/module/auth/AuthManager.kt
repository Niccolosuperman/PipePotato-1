package io.github.pipespotatos.module.auth

import io.github.pipespotatos.api.database.Database
import io.github.pipespotatos.api.database.execute
import io.github.pipespotatos.api.database.executeUpdate
import java.util.*


object AuthManager : Database("auth") {

    private const val table = "accounts"

    init {
        createTableIfNotExists()
    }

    private fun createTableIfNotExists() {
        getConnection().executeUpdate("CREATE TABLE IF NOT EXISTS $table (UUID varchar(255), Password varchar(255));") { }
    }

    fun registerPlayer(uuid: UUID, password: String) {
        getConnection().executeUpdate("INSERT INTO $table (UUID, Password) VALUES (?, ?);") {
            setString(1, uuid.toString().replace("-", ""))
            setString(2, password)
        }
    }

    fun unregisterPlayer(uuid: UUID) {
        getConnection().executeUpdate("DELETE FROM $table WHERE UUID=?;") {
            setString(1, uuid.toString().replace("-", ""))
        }
    }

    fun isPlayerRegistered(uuid: UUID): Boolean {
        var ret = false
        getConnection().execute("SELECT * FROM $table WHERE UUID=?;", {
            setString(1, uuid.toString().replace("-", ""))
        }, {
            while (next())
                ret = true
        })
        return ret
    }

    fun getPlayerPassword(uuid: UUID): String {
        var ret = ""
        getConnection().execute("SELECT Password FROM $table WHERE UUID=?;", {
            setString(1, uuid.toString().replace("-", ""))
        }, {
            while (next())
                ret = getString(1)
        })
        return ret
    }

}
package io.github.pipespotatos.module.auth

import io.github.pipespotatos.api.database.Database
import java.util.*


object AuthManager : Database("auth") {

    private const val table = "accounts"

    init {
        createTableIfNotExists()
    }

    private fun createTableIfNotExists() {
        val sql = "CREATE TABLE IF NOT EXISTS $table (UUID varchar(255), Password varchar(255));"
        val connection = getConnection()
        val statement = connection.prepareStatement(sql)

        statement.executeUpdate()

        statement.close()
        connection.close()
    }

    fun insertPlayer(uuid: UUID, password: String) {
        if (isPlayerRegistered(uuid))
            return

        val sql = "INSERT INTO $table (UUID, Password) VALUES (?, ?);"
        val connection = getConnection()
        val statement = connection.prepareStatement(sql)
        statement.setString(1, uuid.toString())
        statement.setString(2, password)

        statement.executeUpdate()

        statement.close()
        connection.close()
    }

    fun getPlayerPassword(uuid: UUID): String {
        var ret = ""

        val sql = "SELECT Password FROM $table WHERE UUID=?;"
        val connection = getConnection()
        val statement = connection.prepareStatement(sql)
        statement.setString(1, uuid.toString())
        val results = statement.executeQuery()

        while (results.next())
            ret = results.getString(1)

        connection.close()
        statement.close()
        results.close()

        return ret
    }

    fun isPlayerRegistered(uuid: UUID): Boolean {
        var ret = false

        val sql = "SELECT * FROM $table WHERE UUID=?"
        val connection = getConnection()
        val statement = connection.prepareStatement(sql)
        statement.setString(1, uuid.toString())
        val results = statement.executeQuery()

        while (results.next())
            ret = true
        statement.close()
        results.close()
        connection.close()

        return ret
    }

}
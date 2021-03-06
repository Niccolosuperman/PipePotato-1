package io.github.pipespotatos.api.database

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

fun Connection.execute(sql: String, params: PreparedStatement.() -> Unit, output: ResultSet.() -> Unit) {
    prepareStatement(sql).run {
        params()

        val results = executeQuery()

        results.output()
        results.close()

        close()
    }

    close()
}

fun Connection.executeUpdate(sql: String, params: PreparedStatement.() -> Unit) {
    prepareStatement(sql).run {
        params()

        executeUpdate()
        close()
    }

    close()
}
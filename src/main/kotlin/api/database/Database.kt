package io.github.pipespotatos.api.database

import org.spongepowered.api.Sponge
import org.spongepowered.api.service.sql.SqlService
import java.sql.Connection
import javax.sql.DataSource

open class Database(private val name: String) {

    private lateinit var sql: SqlService

    fun getConnection(): Connection {
        return getDataSource(getUri()).connection
    }

    private fun getDataSource(jdbcUrl: String): DataSource {
        if (!::sql.isInitialized) {
            sql = Sponge.getServiceManager().provide(SqlService::class.java).get()
        }
        return sql.getDataSource(jdbcUrl)
    }

    private fun getUri(): String {
        return "jdbc:h2:./$name"
    }

}


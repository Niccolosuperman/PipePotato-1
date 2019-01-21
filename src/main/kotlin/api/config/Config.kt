@file:Suppress("UnstableApiUsage")

package io.github.pipespotatos.api.config

import com.google.common.reflect.TypeToken
import io.github.pipespotatos.extensions.ifNull
import ninja.leaping.configurate.hocon.HoconConfigurationLoader
import ninja.leaping.configurate.objectmapping.ObjectMappingException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.IOException

val logger: Logger = LoggerFactory.getLogger("pipepotato/Config")

inline fun <reified T> loadConfig(config: File): T {
    val configManager = HoconConfigurationLoader.builder().setPath(config.toPath()).build()

    try {
        return configManager.load().getValue(TypeToken.of(T::class.java)).ifNull {
            logger.info("RolesConfig $config is null, generating...")

            val node = configManager.createEmptyNode()
            node.setValue(TypeToken.of(T::class.java), T::class.java.newInstance())

            configManager.save(node)

            T::class.java.newInstance()
        }
    } catch (e: ObjectMappingException) {
        logger.error("Failed to load config $config - Using a default", e)
    } catch (e: IOException) {
        logger.error("Failed to load config $config - Using a default", e)
    }

    return T::class.java.newInstance()
}

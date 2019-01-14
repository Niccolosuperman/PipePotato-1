package io.github.pipespotatos.api.module

import org.slf4j.Logger
import org.slf4j.LoggerFactory

open class Module(id: String = "module", val name: String = id) : BaseModule(id) {

    protected val logger: Logger = LoggerFactory.getLogger("module/${name}")

    override var isEnabled = false

    override fun onEnable() {
        logger.info("$name module enabled.")
    }

    override fun onDisable() {
        logger.info("$name module disabled.")
    }

}
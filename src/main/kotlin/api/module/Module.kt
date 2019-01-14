package io.github.pipespotatos.api.module

import org.slf4j.Logger

class Module(id: String = "module", val name: String = id) : BaseModule(id) {

    override var isEnabled = false

    private lateinit var logger: Logger

    override fun onEnable() {
        logger.info("$name module enabled.")
    }

    override fun onDisable() {
        logger.info("$name module disabled.")
    }

    fun setLogger(logger: Logger) {
        this.logger = logger
    }

}
package io.github.pipespotatos.api.module

import org.slf4j.Logger

open class Module(id: String = "module", val name: String = id) : BaseModule(id) {

    override var isEnabled = false

    lateinit var logger: Logger

    override fun onEnable() {
        logger.info("$name module enabled.")
    }

    override fun onDisable() {
        logger.info("$name module disabled.")
    }

}
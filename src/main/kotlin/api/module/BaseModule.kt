package io.github.pipespotatos.api.module

abstract class BaseModule(val id: String) {

    abstract var isEnabled: Boolean

    abstract fun onEnable()

    abstract fun onDisable()

}
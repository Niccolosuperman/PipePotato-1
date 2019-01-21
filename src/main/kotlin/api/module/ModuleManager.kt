package io.github.pipespotatos.api.module

object ModuleManager {

    private val modules = mutableSetOf<Module>()
    internal val classRegistry = mutableSetOf<Any>()

    fun startAllModules() {
        for (module in modules)
            startModule(module)
    }

    fun stopAllModules() {
        for (module in modules)
            stopModule(module)
    }

    fun startModule(module: Module) {
        if (module.isEnabled)
            return // @todo Throw 'already enabled exception' at module initialization
        module.isEnabled = true

        module.onEnable()
    }

    fun stopModule(module: Module) {
        if (!module.isEnabled)
            return // @todo Throw 'already disabled exception' at module initialization
        module.isEnabled = false

        module.onDisable()
    }

    fun registerModule(module: Module) {
        // @todo Create module registry and class map
        modules.add(module)
    }

    fun unregisterModule(module: Module) {
        modules.remove(module)
    }

    fun registerClass(clazz: Any) {
        classRegistry.add(clazz)
    }

    internal inline fun <reified T> getClass(): T = classRegistry.first { it is T } as T

    fun getModuleById(id: String) = modules.firstOrNull { module -> module.id == id }

}
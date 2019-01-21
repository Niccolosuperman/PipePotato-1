package api.module

import io.github.pipespotatos.api.module.Module
import io.github.pipespotatos.api.module.ModuleManager
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class TestModule : Module("test")
class TestModule2 : Module("test2")

class ModuleTests {

    @Test
    fun testModuleAdd() {
        ModuleManager.registerModule(TestModule())
        Assertions.assertNotNull(ModuleManager.getModuleById("test"))
    }

    @Test
    fun testModuleRemove() {
        ModuleManager.registerModule(TestModule())
        ModuleManager.unregisterModule(ModuleManager.getModuleById("test")!!)

        Assertions.assertNull(ModuleManager.getModuleById("test"))
    }

    @Test
    fun testModuleStart() {
        ModuleManager.registerModule(TestModule())
        ModuleManager.startModule(ModuleManager.getModuleById("test")!!)

        Assertions.assertTrue(ModuleManager.getModuleById("test")!!.isEnabled)
    }

    @Test
    fun testModuleStartAll() {
        ModuleManager.registerModule(TestModule())
        ModuleManager.registerModule(TestModule2())

        ModuleManager.startAllModules()

        Assertions.assertTrue(ModuleManager.getModuleById("test")!!.isEnabled)
        Assertions.assertTrue(ModuleManager.getModuleById("test2")!!.isEnabled)
    }
    @Test
    fun testModuleStop() {
        ModuleManager.registerModule(TestModule())

        ModuleManager.startModule(ModuleManager.getModuleById("test")!!)
        ModuleManager.stopModule(ModuleManager.getModuleById("test")!!)

        Assertions.assertFalse(ModuleManager.getModuleById("test")!!.isEnabled)
    }

    @Test
    fun testModuleStopAll() {
        ModuleManager.registerModule(TestModule())
        ModuleManager.registerModule(TestModule2())

        ModuleManager.startAllModules()
        ModuleManager.stopAllModules()

        Assertions.assertFalse(ModuleManager.getModuleById("test")!!.isEnabled)
        Assertions.assertFalse(ModuleManager.getModuleById("test2")!!.isEnabled)
    }

}
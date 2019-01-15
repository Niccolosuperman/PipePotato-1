import io.github.pipespotatos.extensions.ifNull
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class GenericExtensionTests {

    @Test
    fun ifNullTestNull() {
        val a: String? = null
        val b = a.ifNull { "test" }

        Assertions.assertEquals("test", b)
    }

    @Test
    fun ifNullTestNotNull() {
        val a: String? = "hello"
        val b = a.ifNull { "test" }

        Assertions.assertEquals("hello", b)
    }

}
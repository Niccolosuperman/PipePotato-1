package io.github.pipespotatos.extensions

fun <T> T?.ifNull(f: () -> T): T {
    if (this == null) {
        return f()
    }

    return this
}
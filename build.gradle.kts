import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.11"

    id("org.spongepowered.plugin") version "0.9.0"
}

group = "io.github.pipespotatos"
version = "0.2.2"
description = "Core plugin which features all the essentials and mechanics of the official server"

repositories {
    mavenCentral()
}

dependencies {
    compile(kotlin("stdlib-jdk8"))
    compile(kotlin("reflect"))
    compile("at.favre.lib:bcrypt:0.6.0")

    compileOnly("org.spongepowered:spongeapi:7.1.0")
    testCompile("org.spongepowered:spongeapi:7.1.0")

    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("io.mockk:mockk:1.9")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.withType<Test> {
    useJUnitPlatform()
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"

    exclude("META-INF/*")
    from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it) })

    with(tasks["jar"] as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}

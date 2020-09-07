package org.wolfpack.assessment

import org.springframework.boot.runApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class PackApplication

fun main(args: Array<String>) {
    /**
     * Kotlin alternative to:
     * SpringApplication.run(PackApplication::class.java, *args)
     */
    runApplication<PackApplication>(*args)
}

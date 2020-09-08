package org.wolfpack.assessment

import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.geo.Point
import java.time.LocalDate

@Configuration
class Configuration {

    @Bean
    fun databaseInitializer(
        wolfRepository: WolfRepository,
        packRepository: PackRepository
    ) = ApplicationRunner {

        val joep = wolfRepository.save(Wolf("Joep Klein Teeselink", "Male", LocalDate.of(1994, 2, 5), Point(0.0, 0.0)))
        val joch = wolfRepository.save(Wolf("Joch Jansz", "Male", LocalDate.of(1993, 9, 14), Point(0.0, 0.0)))

        packRepository.save(
            Pack(
                name = "Big Pack",
                wolves = listOf(joep, joch)
            )
        )

        packRepository.save(
            Pack(
                name = "Running Pack",
                wolves = listOf()
            )
        )
    }

}
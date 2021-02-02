package org.wolfpack.assessment

import com.ninjasquad.springmockk.MockkBean
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.data.geo.Point
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.wolfpack.assessment.models.Location
import org.wolfpack.assessment.models.Wolf
import org.wolfpack.assessment.services.WolfService
import java.time.LocalDate

@ExtendWith(SpringExtension::class)
@AutoConfigureMockMvc(addFilters = false)
internal class WolfControllerTest {

    @MockkBean
    lateinit var wolfService: WolfService

    @BeforeEach
    fun clearData() {
        wolfService.deleteAllWolves()
    }

    @Test
    fun `Creating a new wolf should then return that user`() {
        val wolf = Wolf(
            "Joep 2",
            "Male",
            LocalDate.of(1994, 2, 5),
            Location(0.0, 0.0)
        )
        wolfService.createWolf(wolf)
        verify { wolfService.findWolfById("Joep 2") }
    }
}
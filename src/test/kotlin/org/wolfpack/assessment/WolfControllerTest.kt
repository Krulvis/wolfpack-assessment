package org.wolfpack.assessment

import com.fasterxml.jackson.annotation.JsonTypeInfo
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.data.geo.Point
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.LocalDate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WolfControllerTest @Autowired constructor(
    private val wolfRepository: WolfRepository,
    private val restTemplate: TestRestTemplate
) {
    private val defaultPatientId = ObjectId.get()

    @LocalServerPort
    protected var port: Int = 8080

    @BeforeEach
    fun setUp() {
//        wolfRepository.deleteAll()
    }


    private fun getRootUrl(): String? = "http://localhost:$port/api/wolf"

    private fun saveOnePatient() = wolfRepository.save(
        Wolf("Joep", "Male", LocalDate.of(1994, 2, 5), Point(0.0, 0.0))
    )

    @Test
    fun `should return all patients`() {
        saveOnePatient()

        val response = restTemplate.getForEntity(
            getRootUrl(),
            List::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(1, response.body?.size)
    }

    @Test
    fun `should return single patient by id`() {
        saveOnePatient()

        val response = restTemplate.getForEntity(
            getRootUrl() + "/$defaultPatientId",
            Wolf::class.java
        )

        assertEquals(200, response.statusCode.value())
        assertNotNull(response.body)
        assertEquals(defaultPatientId, response.body?.id)
    }
}
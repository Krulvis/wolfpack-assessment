package org.wolfpack.assessment

import org.hamcrest.Matchers
import org.json.JSONArray
import org.json.JSONObject
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.wolfpack.assessment.services.PackService
import org.wolfpack.assessment.services.WolfService

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
class WolfControllerTest {


    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var wolfService: WolfService

    @MockBean
    private lateinit var packService: PackService

    @BeforeEach
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Create new wolf`() {
        val wolf = JSONObject(
            mapOf(
                Pair("name", "TestUser"),
                Pair("birthday", "1994-02-20"),
                Pair("gender", "Male"),
                Pair("location", JSONObject("""{"longitude":"1.2", "latitude":"2.1"}"""))
            )
        )
        mockMvc.post("/api/wolf/") {
            contentType = MediaType.APPLICATION_JSON
            content = wolf.toString()
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isCreated }
        }
    }

    @Test
    fun `Create wolf with missing values`() {
        val wolf = JSONObject(
            mapOf(
//                Pair("name", "TestUser"),
                Pair("birthday", "1934-02-20"),
                Pair("gender", "Male"),
                Pair("location", JSONObject("""{"longitude":"1.2", "latitude":"2.1"}"""))
            )
        )
        mockMvc.post("/api/wolf/") {
            contentType = MediaType.APPLICATION_JSON
            content = wolf.toString()
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest }
        }.andExpect {
            content { string(Matchers.containsString("[name] must not be null")) }
        }
    }

    @Test
    fun `Create wrong birthday wolf`() {
        val wolf = JSONObject(
            mapOf(
                Pair("name", "TestUser"),
                Pair("birthday", "194-02-20"),
                Pair("gender", "Male"),
                Pair("location", JSONObject("""{"longitude":"1.2", "latitude":"2.1"}"""))
            )
        )
        mockMvc.post("/api/wolf/") {
            contentType = MediaType.APPLICATION_JSON
            content = wolf.toString()
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest }
            content { string(Matchers.containsString("Incorrect format for: [birthday]")) }
        }
    }

    @Test
    fun `Create pack without any wolves`() {
        val wolf = wolfService.findWolfByName("TestUser")
        val pack = JSONObject(
            mapOf(
                Pair("name", "NewPack")
//                Pair("wolves", listOf(wolf))
            )
        )
        mockMvc.post("/api/pack/") {
            contentType = MediaType.APPLICATION_JSON
            content = pack.toString()
            accept = MediaType.APPLICATION_JSON
        }.andExpect {
            status { isBadRequest }
            content { string(Matchers.containsString("[wolves] must not be null")) }
        }
    }

}
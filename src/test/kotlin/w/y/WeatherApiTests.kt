package w.y

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.*

class WeatherApiTests {
    @Test
    fun testWeatherUSA() {
        withTestApplication(Application::module) {
            handleRequest(HttpMethod.Get, "/weather/usa").apply {
                assertEquals(HttpStatusCode.OK, response.status())
                response.content?.endsWith("USA weather: sunny")?.let { assertTrue(it) }
            }
        }
    }
}
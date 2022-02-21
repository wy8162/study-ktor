package w.y.tutorial.client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*

object HttpClientBuilder {
    fun build(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(JsonFeature)
        }
    }
}


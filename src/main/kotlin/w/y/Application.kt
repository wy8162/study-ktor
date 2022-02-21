package w.y

import io.ktor.application.*
import io.ktor.client.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import io.ktor.util.*
import kotlinx.serialization.json.*
import org.slf4j.event.*
import w.y.tutorial.*
import w.y.tutorial.route.*


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(DefaultHeaders) {
        header("X-Engine", "Ktor") // will send this header with each response
    }
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }
    install(Routing) {
        get("/hello") {
            call.respondText("Hello, World")
        }
    }
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    registerStaticRoute()
    registerCustomerRoutes()
    registerOrderRoutes()
    registerClientRoutes()


    // Some routes for reference
    routing {
        //trace { application.log.trace(it.buildText()) }
        route("/weather") {
            get("/usa") {
                val state = call.request.queryParameters.toMap()["state"] ?: "The whole country"
                call.respondText("$state USA weather: sunny")
            }

            // curl "localhost:8080/weather/europe?country=germany"
            route("/europe") {
                //optionalParam()
                param("country") {
                    handle {
                        val country = call.parameters["country"]
                        call.respondText("$country in Europe weather is: cold")
                    }
                }
            }

            // curl -H "region:east" -X GET  "localhost:8080/weather/asia?country=china"
            route("asia", HttpMethod.Get) {
                header("region", "east") {
                    optionalParam("country") {
                        handle {
                            val country = call.parameters["country"] ?: "All Asia countries"
                            call.respondText("$country in Asia weather is: cold")
                        }
                    }
                }
            }
        }
    }

    configureShutdownHook(ApplicationContext.httpClient)
}

private fun Application.configureShutdownHook(httpClient: HttpClient) {
    environment.monitor.subscribe(ApplicationStopping) {
        log.info("Shutting down the application ...")
        httpClient.close()
    }
}
package w.y

import io.ktor.application.*
import io.ktor.auth.*
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
import w.y.bookstore.*
import w.y.tutorial.*
import w.y.tutorial.route.*


fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {

    // Handles failures and provide more information.
    install(StatusPages) {
        statusFile(
            HttpStatusCode.InternalServerError,
            HttpStatusCode.NotFound,
            filePattern = "errors/error404.html"
        )
        exception<NotFoundException> { cause ->
            call.respond(HttpStatusCode.NotFound)
            log.error(cause.localizedMessage)
            throw cause
        }
        exception<BadRequestException> { cause ->
            call.respondText("Bad request.")
            log.error(cause.localizedMessage)
            throw cause
        }
    }

    authentication {
        val username = environment.config.propertyOrNull("server.test.username")?.getString()
        val password = environment.config.propertyOrNull("server.test.password")?.getString()

        // ClientRoutes uses these authentications.
        basic(name = "basicAuth") {
            realm = "basicAuthRealm"
            validate { credentials ->
                if (credentials.name == username && credentials.password == password) {
                    UserIdPrincipal(credentials.name)
                } else {
                    null
                }
            }
        }
    }

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
        // Install JSON converter
        json(Json {
            prettyPrint = true
            isLenient = true
        })
    }

    registerStaticRoute()
    registerCustomerRoutes()
    registerOrderRoutes()
    registerClientRoutes()
    registerBookStoreRoutes()


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
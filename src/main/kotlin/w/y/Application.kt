package w.y

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import io.ktor.server.netty.*
import w.y.route.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    registerStaticRoute()
    registerCustomerRoutes()
    registerOrderRoutes()
}
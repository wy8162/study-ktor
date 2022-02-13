package w.y.route

import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerStaticRoute() {
    routing {
        staticRoute()
    }
}

fun Route.staticRoute() {
    route("/static") {
        static("/") {
            resources("/files")
        }
        get("/customer") {

        }

    }
}

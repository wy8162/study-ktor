package w.y.tutorial.route

import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.content.*
import io.ktor.routing.*
import kotlinx.html.*

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
            call.respondHtml {
                head {
                    title("ktor Customer Application")
                }
                body {
                    h1 { +"Customers Accounts" }
                    p {
                        +"Details of Customers Accounts"
                    }
                }
            }
        }

    }
}

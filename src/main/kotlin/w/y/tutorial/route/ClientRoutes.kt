package w.y.tutorial.route

import io.ktor.application.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import w.y.tutorial.*
import w.y.tutorial.model.*

fun Application.registerClientRoutes() {
    routing {
        trace { application.log.trace(it.buildText()) }
        apiClient()
    }
}

/**
 * Routes to consume the APIs
 */
private const val URL_CUSTOMER = "http://localhost:8080/api/v1/customer"
private const val URL_ORDER = "http://localhost:8080/api/v1/order"

private fun Route.apiClient() {
    route("/client") {
        route("/customer") {
            get {
                // Get all customers
                val result = ApplicationContext.httpClient.get<Array<Customer>>(URL_CUSTOMER) {
                    headers {
                        append(HttpHeaders.Accept, "application/json")
                    }
                }

                call.respond(result)
            }
            get("/{id}") {
                // Get customer by Id
                val id = call.parameters["id"]
                val response = ApplicationContext.httpClient.get<HttpResponse>("$URL_CUSTOMER/$id")

                when( response.status ) {
                    HttpStatusCode.OK -> {
                        val customer = response.receive<Customer>()
                        call.respond(customer)
                    }
                    else -> call.respondText("INVALID ID", status = HttpStatusCode.BadRequest)
                }
            }
        }
        route("/order") {
            get("/") {
                // get all orders

            }
            get("/{id}") {
                // get order by Id
            }
        }
    }
}

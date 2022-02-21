package w.y.tutorial.route

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import w.y.tutorial.client.*
import w.y.tutorial.model.*

fun Application.registerClientRoutes() {
    routing {
        trace { application.log.trace(it.buildText()) }
        apiClient()
    }
}


private fun Route.apiClient() {
    route("/client") {
        route("/customer") {
            get {
                // Get all customers
                val result = Backend.getAllCustomers()
                call.respond(result)
            }
            get("/{id}") {
                // Get customer by Id
                val response = Backend.getCustomerById(call.parameters["id"])

                when( response ) {
                    is Customer -> call.respond(response)
                    else -> call.respondText("INVALID ID", status = HttpStatusCode.BadRequest)
                }
            }
        }
        route("/order") {
            get {
                // get all orders
                val result = Backend.getAllOrders()
                call.respond(result)
            }
            get("/{id}") {
                // get order by Id
                val response = Backend.getOrderById(call.parameters["id"])

                when( response ) {
                    is Order -> call.respond(response)
                    else -> call.respondText("INVALID ID", status = HttpStatusCode.BadRequest)
                }
            }
        }
    }
}

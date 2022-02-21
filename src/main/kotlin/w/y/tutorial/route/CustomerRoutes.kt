package w.y.tutorial.route

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import w.y.tutorial.model.*

fun Application.registerCustomerRoutes() {
    routing {
        customerRouting()
    }
}

fun Route.customerRouting() {
    route("/api/v1/customer") {
        get {
            if (customerStorage.isNotEmpty()) {
                call.respond(customerStorage)
            } else {
                call.respondText("NOT FOUND", contentType = ContentType.Text.Plain, status = HttpStatusCode.NotFound)
            }
        }
        get("{id}") {
            val id = call.parameters["id"] ?: return@get call.respondText(
                "BAD REQUEST - Invalid ID",
                status = HttpStatusCode.BadRequest
            )

            val customer = customerStorage.find { it.id == id } ?: return@get call.respondText(
                "NOT FOUND - customer does not exist",
                status = HttpStatusCode.NotFound
            )

            call.respond(status = HttpStatusCode.OK, customer)
        }
        post {
            val customer = call.receive<Customer>()

            customerStorage.add(customer)
            call.respondText("Customer Created", contentType = ContentType.Text.Plain, status = HttpStatusCode.Created)
        }
        delete("{id}") {
            val id = call.parameters["id"] ?: return@delete call.respond(
                status = HttpStatusCode.BadRequest,
                "BAD REQUEST - Invalid ID"
            )

            if (customerStorage.removeIf { it.id == id}) {
                call.respondText("DELETED", contentType = ContentType.Text.Plain, status = HttpStatusCode.Accepted)
            } else {
                call.respondText("NOT FOUND", contentType = ContentType.Text.Plain, status = HttpStatusCode.NotFound)
            }
        }
    }
}

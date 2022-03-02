package w.y.tutorial.route

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import w.y.tutorial.error.*
import w.y.tutorial.model.*

fun Application.registerOrderRoutes() {
    routing {
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}

fun Route.listOrdersRoute() {
    get("/api/v1/order") {
        if (orderStorage.isNotEmpty()) {
            call.respond(orderStorage)
        }
    }
}

fun Route.getOrderRoute() {
    get("/api/v1/order/{id}") {
        val id = call.parameters["id"] ?: throw BadRequestException("Invalid order id")
        val order = orderStorage.find { it.number == id } ?: throw NotFoundException("Order for $id not found")
        call.respond(order)
    }
}

fun Route.totalizeOrderRoute() {
    get("/api/v1/order/{id}/total") {
        val id = call.parameters["id"] ?: throw BadRequestException("Invalid order id")
        val order = orderStorage.find { it.number == id } ?: throw NotFoundException("Order for $id not found")

        val total = order.contents.map { it.price * it.amount }.sum()
        call.respond(total)
    }
}

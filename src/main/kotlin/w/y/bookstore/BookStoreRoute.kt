package w.y.bookstore

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Application.registerBookStoreRoutes() {
    routing {
        bookStoreRoute()
    }
}

private fun Route.bookStoreRoute() {
    route("/book") {
        get {
            call.respond(BookStore.getAllBooks())
        }
        get("/{id}") {
            val id: String? = call.parameters["id"]
            val book = if (id != null) BookStore.getBookById(id) else null

            if (book == null)
                call.respondText(
                    "BAD REQUEST - Invalid ID",
                    status = HttpStatusCode.BadRequest
                )
            else
                call.respond(status = HttpStatusCode.OK, book)
        }
        put { // Add new book
            // Receive data and deserialize it to object
            // https://ktor.io/docs/serialization.html#receive_data
            // The content type of the request will be used to choose the
            // converter.
            //
            // To send data, the converter is chosen based on the Accept header.
            var bookDto = call.receive<BookDto>()
            val book = BookStore.addBook(bookDto)
            call.respond(status = HttpStatusCode.Created, book!!)
        }
        post("/{id}") { // Update new book
            val bookDto = call.receive(BookDto::class) // Another way to receive
            val id = call.parameters["id"]

            if (id == null || bookDto == null)
                return@post call.respondText("BAD REQUEST - Invalid ID", status = HttpStatusCode.BadRequest)

            val book = BookStore.updateBook(Book(id, bookDto.name, bookDto.authorId, bookDto.qty))
            if (book != null) call.respond(status = HttpStatusCode.Created, book)
            else call.respondText("BAD REQUEST - Invalid ID", status = HttpStatusCode.BadRequest)
        }
        delete("/{id}") {
            val id: String? = call.parameters["id"]
            val book = if (id != null) BookStore.deleteBookById(id) else null

            if (book == null)
                call.respondText("BAD REQUEST - Invalid ID", status = HttpStatusCode.BadRequest)
            else
                call.respond(status = HttpStatusCode.OK, book)
        }
    }
}

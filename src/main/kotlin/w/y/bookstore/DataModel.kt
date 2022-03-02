package w.y.bookstore

import kotlinx.serialization.Serializable

@Serializable
data class Book(val id: String, val name: String, val authorId: String, var qty: Int)

@Serializable
data class BookDto(val name: String, val authorId: String, var qty: Int)

@Serializable
data class ShoppingCart(val id: String, val user: String, val items: List<CartItem> = mutableListOf<CartItem>())

@Serializable
data class CartItem(val bookId : String, var qty: Int, var price: Double)

@Serializable
data class User(val id: String, val name: String)

object BookStore {
    private val bookStore : MutableMap<String, Book>

    init {
        var id = 0
        bookStore = mutableMapOf(
            id.toString() to Book(id++.toString(), "How to Grow Micro Green",               "1", 10),
            id.toString() to Book(id++.toString(), "How to Grow Apples",                    "2", 110),
            id.toString() to Book(id++.toString(), "How to Grow Green Peas",                "1", 101),
            id.toString() to Book(id++.toString(), "How to Program RESTful Web Services",   "3", 210),
            id.toString() to Book(id++.toString(), "Animal Farm",                           "3", 130),
            id.toString() to Book(id++.toString(), "War and Peace",                         "4", 140),
            id.toString() to Book(id++.toString(), "Red Dragon",                            "5", 70),
        )
    }

    fun getAllBooks(): List<Book> {
        return bookStore.values.toList()
    }

    fun getBookById(id: String): Book? {
        return bookStore[id]
    }

    fun addBook(bookDto: BookDto): Book? {
        val id = bookStore.size.toString()
        bookStore[id] = Book(id, bookDto.name, bookDto.authorId, bookDto.qty)
        return bookStore[id]
    }

    fun deleteBookById(id: String): Book? {
        return bookStore.remove(id)
    }

    fun updateBook(book: Book): Book? {
        return bookStore[book.id]?.run {
            bookStore[book.id] = Book(id, book.name, book.authorId, book.qty)
            bookStore[book.id]
        }
    }
}
package w.y.tutorial.model

import kotlinx.serialization.Serializable

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)

val customerStorage = mutableListOf<Customer>(
    Customer("001", "Steve", "Wang", "steve@gmail.com"),
    Customer("002", "Stephen", "Wang", "stephen@gmail.com"),
    Customer("003", "Sam", "Wang", "sam@gmail.com")
)
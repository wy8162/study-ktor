package w.y.tutorial.client

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import w.y.tutorial.model.*

/**
 * Routes to consume the APIs
 */
private const val URL_CUSTOMER = "http://localhost:8080/api/v1/customer"
private const val URL_ORDER = "http://localhost:8080/api/v1/order"


object Backend {
    private val httpClient = HttpClient(CIO) {
            install(Logging)
            install(JsonFeature)
    }

    fun getHttpClient(): HttpClient = httpClient

    suspend fun getAllCustomers(): Array<Customer> {
        val result = httpClient.get<Array<Customer>>(URL_CUSTOMER) {
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }

        return result
    }

    suspend fun getCustomerById(id: String?): Customer? {
        val response = httpClient.get<HttpResponse>("$URL_CUSTOMER/$id")

        return when( response.status )
        {
            HttpStatusCode.OK -> response.receive<Customer>()
            else -> null
        }

    }

    suspend fun getAllOrders(): Array<Order> {
        val result = httpClient.get<Array<Order>>(URL_ORDER) {
            headers {
                append(HttpHeaders.Accept, "application/json")
            }
        }

        return result
    }

    suspend fun getOrderById(id: String?): Order? {
        val response = httpClient.get<HttpResponse>("$URL_ORDER/$id")

        when( response.status ) {
            HttpStatusCode.OK -> {
                return response.receive<Order>()
            }
            else -> return null
        }

    }
}


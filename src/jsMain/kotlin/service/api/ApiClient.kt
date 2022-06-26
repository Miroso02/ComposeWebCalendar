package service.api

import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*

object ApiClient {

    const val baseUrl = "http://localhost:8080/"

    val client = HttpClient(Js) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = kotlinx.serialization.json.Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    suspend inline fun <reified T> get(
        path: String,
    ): T = client.get("$baseUrl$path") {
        accept(ContentType.Application.Json)
    }

    suspend inline fun post(
        path: String,
        body: Any
    ): HttpResponse =  client.post("$baseUrl$path") {
        contentType(ContentType.Application.Json)
        this.body = body
    }
}
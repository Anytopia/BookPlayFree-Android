package com.zachnr.bookplayfree.network.ext

import com.zachnr.bookplayfree.network.model.ResponseWrapper
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.url
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.reflect.typeInfo

suspend inline fun <reified T> HttpResponse.bodyWrapped(): ResponseWrapper<T> {
    return try {
        when (status.value) {
            // Success block
            200 -> {
                ResponseWrapper.Success(call.bodyNullable(typeInfo<T>()) as T)
            }
            // Not Success block
            else -> {
                ResponseWrapper.Error(
                    code = status.value,
                    message = bodyAsText()
                )
            }
            // Add new conditional branch here when needed. (Ex: EmptyState)
        }
    } catch (e: Exception) {
        ResponseWrapper.Exception(throwable = e)
    }
}

suspend inline fun <reified T> HttpClient.safePostWrapped(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit = {}
): ResponseWrapper<T> = try {
    post {
        url(urlString); block()
    }.bodyWrapped<T>()
} catch (e: Exception) {
    ResponseWrapper.Exception(throwable = e)
}

package org.example.types.dto

import org.springframework.http.HttpStatus
import java.net.http.HttpResponse

object ResponseProvider {
    fun <T> success(result: T): Response<T> {
        return Response(HttpStatus.OK.value(), "SUCCESS", result)
    }

    fun <T> failed(code: HttpStatus, message: String, result: T? = null): Response<T> {
        return Response(HttpStatus.OK.value(), "SUCCESS", result)
    }
}

data class Response<T> (
    val code: Int,
    val message: String,
    val result: T?
)

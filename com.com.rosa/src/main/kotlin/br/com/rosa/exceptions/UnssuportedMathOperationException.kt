package br.com.rosa.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.*

@ControllerAdvice
class UnssuportedMathOperationException(exception: String?) : RuntimeException(exception) {

    @ExceptionHandler(UnssuportedMathOperationException::class)
    fun handleBadRequestException(ex: Exception, request: WebRequest) :
            ResponseEntity<ExceptionResponse> {
        val exceptionResponse =  ExceptionResponse(
            Date(),
            ex.message,
            request.getDescription(false)
        )

        return ResponseEntity<ExceptionResponse>(exceptionResponse, HttpStatus.BAD_REQUEST)
    }
}
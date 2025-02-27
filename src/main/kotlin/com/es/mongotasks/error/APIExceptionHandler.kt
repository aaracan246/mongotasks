package com.es.mongotasks.error

import com.es.mongotasks.error.exception.BadRequestException
import com.es.mongotasks.error.exception.ForbiddenException
import com.es.mongotasks.error.exception.NotFoundException
import jakarta.servlet.http.HttpServletRequest


import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class APIExceptionHandler {

    @ExceptionHandler(
        BadRequestException::class,
        IllegalArgumentException::class,
        NumberFormatException::class,

        )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handleBadRequest(request: HttpServletRequest, e: Exception) : ErrorResponse {
        return ErrorResponse(e.message!!, request.requestURI)
    }

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handleNotFound(request: HttpServletRequest, e: Exception) : ErrorResponse {
        return ErrorResponse(e.message!!, request.requestURI)
    }

    @ExceptionHandler(ForbiddenException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    fun forbidden(request: HttpServletRequest, e: Exception): ErrorResponse{
        return ErrorResponse(e.message!!, request.requestURI)
    }
}
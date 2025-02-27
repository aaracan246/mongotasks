package com.es.mongotasks.error.exception

class ForbiddenException(message: String) : RuntimeException("Not Authorized Exception (401). $message") {
}
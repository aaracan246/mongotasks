package com.es.mongotasks.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.stereotype.Indexed

@Document("Usuario")
data class Usuario(
    @BsonId
    val _id: String?,
    val username: String,
    val password: String,
    val email: String,
    val roles: String = "USER",
    val direccion: Direccion,
    val listaTareas: List<Tarea>? = listOf()
)

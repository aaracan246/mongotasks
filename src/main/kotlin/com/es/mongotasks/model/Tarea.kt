package com.es.mongotasks.model

import org.bson.codecs.pojo.annotations.BsonId
import org.springframework.data.mongodb.core.mapping.Document

@Document("Tarea")
data class Tarea(
    @BsonId
    val _id: String?,
    val titulo: String,
    val desc: String,
    val status: Boolean,
    val usuario: Usuario
    )

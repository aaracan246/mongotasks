package com.es.mongotasks.dto

import com.es.mongotasks.model.Direccion

data class UsuarioDTO(
    val username: String,
    val rol: String?,
    val direccion: Direccion
)

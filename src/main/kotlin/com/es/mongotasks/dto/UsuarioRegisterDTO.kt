package com.es.mongotasks.dto

import com.es.mongotasks.model.Direccion

data class UsuarioRegisterDTO(
    val username: String,
    val password: String,
    val passwordRepeat: String,
    val email: String,
    val rol: String,
    val direccion: Direccion
)
